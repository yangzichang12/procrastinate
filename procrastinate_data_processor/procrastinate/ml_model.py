from nbconvert.preprocessors import ExecutePreprocessor
from django.conf import settings
import numpy as np
import pandas as pd
import sklearn
from procrastinate_data_processor import settings
import torch
from transformers import AutoModelForSpeechSeq2Seq, AutoProcessor, pipeline
import scipy
from datasets import load_dataset
from pydub import AudioSegment
import librosa
import pickle
import os

static_dir = settings.STATIC_DIR
print("Static directory:", static_dir)

def execute_speech_to_text_model(file_path, uploadId):
    ### Loading model to device

    if os.name == 'nt':
        file_path = file_path.replace('/', '\\')

    device = "cuda:0" if torch.cuda.is_available() else "cpu"
    torch_dtype = torch.float16 if torch.cuda.is_available() else torch.float32
    
    model_id = "openai/whisper-large-v3"
    
    model = AutoModelForSpeechSeq2Seq.from_pretrained(
        model_id, torch_dtype=torch_dtype, low_cpu_mem_usage=True, use_safetensors=True
    )
    model.to(device)
    
    processor = AutoProcessor.from_pretrained(model_id)
    
    pipe = pipeline(
        "automatic-speech-recognition",
        model=model,
        tokenizer=processor.tokenizer,
        feature_extractor=processor.feature_extractor,
        max_new_tokens=128,
        chunk_length_s=30,
        batch_size=16,
        return_timestamps=True,
        torch_dtype=torch_dtype,
        device=device,
    )
    
    dataset = load_dataset("distil-whisper/librispeech_long", "clean", split="validation")
    sample = dataset[0]["audio"]
    
    result = pipe(sample)
    print(result["text"])

    sample_audio_path = file_path
    print(sample_audio_path)

    y, sr = librosa.load(sample_audio_path)
    print(y)
    print(sr)

    output_result = pipe(y, generate_kwargs={"language": "english", "task": "transcribe"})
    print(output_result["text"])

    output_path = os.path.join(static_dir, 'output',uploadId) + ".txt"

    if os.name == 'nt':
        output_path = output_path.replace('/', '\\')

    print(output_path)
    transcribed_text = output_result["text"]
    with open(output_path, "w") as dst:
        dst.write(transcribed_text)
        
    result = {
        "output_path" : output_path,
        "transcribed_text" : transcribed_text
    }
    return result
