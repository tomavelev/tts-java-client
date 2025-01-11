from flask import Flask, request, send_file
import torch
from transformers import VitsModel, AutoTokenizer
import scipy.io.wavfile as wavfile
import numpy as np
import os

app = Flask(__name__)

models_tokenizers_cache = {}

def load_model_and_tokenizer(language):
    if language not in models_tokenizers_cache:
        model = VitsModel.from_pretrained(f"facebook/mms-tts-{language}")
        tokenizer = AutoTokenizer.from_pretrained(f"facebook/mms-tts-{language}")
        models_tokenizers_cache[language] = (model, tokenizer)
    return models_tokenizers_cache[language]




# Define the POST endpoint
@app.route('/generate-audio', methods=['POST'])
def generate_audio_endpoint():
    if request.method == 'POST':
        data = request.get_json()
        text = data['text']
        fileName = data['fileName']
        language = data.get('language', 'eng')  # Default to English if not provided
        model, tokenizer = load_model_and_tokenizer(language)

        inputs = tokenizer(text, return_tensors="pt")

        with torch.no_grad():
            output = model(**inputs).waveform

        # Generate the audio file
        outputContent = output.cpu().float().numpy()

        # Save the audio file to a temporary location
        rate = model.config.sampling_rate
        wavfile.write(fileName, rate, outputContent.T)

        # Send the audio file as a response
        return send_file(fileName, mimetype="audio/wav")



def delete_file(file_name):

    os.remove(file_name)
    # Your logic here to delete the file goes here
    print(f"File {file_name} deleted successfully")


@app.route('/delete_file', methods=['POST'])
def delete_file_endpoint():
    # Get the file name from the request
    file_name = request.json['fileName']

    try:
        # Call the delete_file function
        delete_file(file_name)
        return {'message': 'File deleted successfully'}, 200
    except Exception as e:
        # Return an error message if something goes wrong
        return {'message': 'Error deleting file'}, 500



@app.route('/clean_resources', methods=['POST'])
def clean_resources():
    torch.cuda.empty_cache()
    torch.cuda.device_count()
    torch.cuda.current_device()
    models_tokenizers_cache = {}


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0',  port=3333)