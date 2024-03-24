import mimetypes
import os
from datasets.load import requests
from django.http import response

from procrastinate.models import Uploads
from procrastinate_data_processor import settings
import boto3

bucket_name ='procrastinatingbucket'

s3 = boto3.client(
    's3',
    region_name='sgp1',
    endpoint_url='https://sgp1.digitaloceanspaces.com/',
    aws_access_key_id=os.getenv('access_key'),
    aws_secret_access_key=os.getenv('secret_key')
)
def create_save_path(contentType, uploadId):
    
    if 'audio' in contentType.lower():
        save_path_audio = os.path.join(settings.STATIC_DIR, 'audio',uploadId)
        print('Save path (audio)',save_path_audio)
        return save_path_audio
    else:
        print('Unknown file type')

# An Uploads object/model will be pass in 
def download_and_save_file(upload):

    try:
        print(upload.content_url)
        response = requests.get(upload.content_url)
        save_path = create_save_path(upload.content_type, upload.upload_id)

        if response.status_code == 200:
            with open(save_path, 'wb') as f:
                f.write(response.content)
            return save_path

    except Exception as ex:
        print('An error occured in download and save method')
        return None 

def upload_result(output_path, uploadId, username):
    s3_path = os.path.join('Procrastinate','Procrastinate '+ username,'result',uploadId)
    content_type, _ = mimetypes.guess_type(output_path)
    try:
        s3.upload_file(output_path, bucket_name, s3_path,
                        ExtraArgs={'ACL': 'public-read',
                                    'ContentType': content_type, 
                                    'Metadata':
                                        {'username': username}})
    except Exception as ex:
        print('An error occured in upload result service')
        return None
    
    result_url = f"https://procrastinatingbucket.sgp1.digitaloceanspaces.com/{s3_path}"

    return result_url

def update_db_uploads_resultUrl(id,new_value):
    num_of_rows = Uploads.objects.filter(upload_id=id).update(result_url=new_value)
    if not num_of_rows:
        print('Update db was not successful')
    else:
        print('Db sucessfully updated')
