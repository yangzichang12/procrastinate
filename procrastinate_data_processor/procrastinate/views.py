from django.shortcuts import render, HttpResponse
from .models import get_user

user = get_user()

def index(request):
    my_dict = {'insert_me': "From views.py"}
    return render(request,'procrastinate/index.html', context=my_dict)

def home(request):
    return HttpResponse(user)
