from types import GeneratorType
from django.http import response
from django.shortcuts import render, HttpResponse
from django.views.decorators.csrf import csrf_exempt
from nbformat.v3 import nbformat
from nbconvert import HTMLExporter
from .ml_model import execute_speech_to_text_model
from .models import get_user
from django.views.decorators.http import require_GET, require_http_methods

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

@csrf_exempt
@require_GET
def speechToText(request):
    model_output = execute_speech_to_text_model()
    
    return HttpResponse(model_output)
