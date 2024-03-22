from django.shortcuts import render, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from .models import get_user

user = get_user()

def index(request):
    my_dict = {'insert_me': "From views.py"}
    return render(request,'procrastinate/index.html', context=my_dict)

def home(request):
    return HttpResponse(user)

@csrf_exempt
def pong(request):
    if request.method == 'GET':
        return HttpResponse('pong')
    else:
        return HttpResponse('invalid request',status=400)

