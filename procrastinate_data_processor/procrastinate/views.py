from django.shortcuts import render, HttpResponse
from .models import get_user

user = get_user()

def home(request):
     return HttpResponse(user)
