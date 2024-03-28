from base64 import b64decode
import base64
from functools import wraps
import json
from django.http import JsonResponse
import jwt


class jwtMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response

    def __call__(self, request):
        if request.method == 'POST':
            # Assuming token is sent as a JSON object in the request body
            try:
                jwt_options = {
                    'verify_signature': False,
                    'verify_exp': True,
                    'verify_nbf': False,
                    'verify_iat': True,
                    'verify_aud': False
    }
                data = json.loads(request.body.decode('utf-8'))
                jwt_token = data.get('token')
                print(jwt_token)
                if jwt_token:
                    decoded_token = jwt.decode(jwt_token, algorithms=['HS256'],options=jwt_options)
                    print(decoded_token)
                    request.username = decoded_token.get('sub')
                    print(request.username)
                    request.roles = decoded_token.get('roles', [])
                    print(request.roles)
            except jwt.ExpiredSignatureError:
                return JsonResponse({'error': 'Token is expired'}, status=401)
            except jwt.InvalidTokenError:
                return JsonResponse({'error': 'Invalid token'}, status=401)
        response = self.get_response(request)
        return response
    
    def authenticated_required(self, view_func):
        @wraps(view_func)
        def wrapper(request, *args, **kwargs):
            if hasattr(request, 'username'):
                return view_func(request, *args, **kwargs)
            else:
                return JsonResponse({'error': 'Authentication required'}, status=401)
        return wrapper

    def role_required(self, roles):
        def decorator(view_func):
            @wraps(view_func)
            def wrapper(request, *args, **kwargs):
                if hasattr(request, 'roles'):
                    user_roles = [role['authority'] for role in request.roles]
                    if any(role in user_roles for role in roles):
                        return view_func(request, *args, **kwargs)
                return JsonResponse({'error': 'Insufficient permissions'}, status=403)
            return wrapper
        return decorator