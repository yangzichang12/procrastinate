from enum import unique
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

class Uploads(models.Model):
    upload_id = models.CharField(max_length=8, primary_key=True)
    username = models.CharField(max_length=64, unique=False)
    content_url = models.URLField(max_length=264)
    content_type = models.CharField(max_length=32)

    def __str__(self) -> str:
        return f"Upload ID: {self.upload_id}, User ID: {self.user_id}, Content URL: {self.content_url}, Content Type: {self.content_type}"

class Topics(models.Model):
    topic_id = models.CharField(max_length=8, primary_key=True)
    topic = models.CharField(max_length=128, unique=True)

class Topics_Uploads(models.Model):
    topic_id = models.ForeignKey(Topics, on_delete=models.CASCADE, to_field='topic_id')
    upload_id = models.ForeignKey(Uploads, on_delete=models.CASCADE, to_field='upload_id')

