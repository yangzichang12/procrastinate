from django.db import models
from django.contrib.auth.models import User


# Create your models here.

def get_user():
    try:
        dummy_user = User.objects.get(username='Alejandro_houlu')
        print(dummy_user)
        return dummy_user
    except User.DoesNotExist:
        return None
