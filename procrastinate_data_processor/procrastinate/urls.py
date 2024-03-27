from os import name
from django.urls import path
from . import views

urlpatterns = [
    path("", views.home, name="home"),
    path("index", views.index, name="index"),
    path("ping", views.pong, name='pong'),
    path("api/speechToText", views.speechToText, name='speechToText'),
    path("api/authToken",views.authToken, name='authToken' )
]
