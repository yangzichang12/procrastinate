import { useState } from 'react';
import { allFileTypes } from './models';
import { ERROR_MSG_INVALID_FILE, SUCCESS_MSG, CONSOLE_ERROR_MSG } from './strings';
import { FILE_UPLOAD_API, POST } from '../api/requestUrl';
import { FileUploadRequestBody } from '../api/requestBody';
import {AllowedFileType} from './models';

export const useFileUploadLogic = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [errorMessage, setErrorMessage] = useState<string>('');
  const [uploadProgress, setUploadProgress] = useState<number>(0);
  const [uploadSuccess, setUploadSuccess] = useState<boolean>(false);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const fileType = getFileType(file.name);
      if (fileType && allFileTypes.includes(fileType)) {
        setSelectedFile(file);
        setErrorMessage('');
      } else {
        setSelectedFile(null);
        setErrorMessage(ERROR_MSG_INVALID_FILE);
      }
    }
  };
  
  const getFileType = (fileName: string): AllowedFileType | undefined => {
    const extension = fileName.split('.').pop();
    return extension ? `.${extension}` as AllowedFileType : undefined;
  };
  

  const handleUpload = async () => {
    console.log('handleUpload');
    if (!selectedFile) return;
    try {
      console.log(selectedFile);
      //await simulateUploadProgress();
      const formData = prepareFormData(selectedFile);

      console.log(formData);
      const response = await uploadFormData(formData);

      if (response.ok) {
        handleUploadSuccess();
      } else {
        handleUploadError();
      }
    } catch (error) {
      handleUploadError(error);
    }
  };

  const simulateUploadProgress = async () => {
    for (let i = 0; i <= 100; i++) {
      setUploadProgress(i);
      await new Promise(resolve => setTimeout(resolve, 50)); // Simulate delay
    }
  };

  const prepareFormData = (selectedFile: File) => {
    console.log('prepareFormData');
    const requestBody: FileUploadRequestBody = {
      username: 'jane12',
      email: 'jane@gmail.com',
      audioFile: selectedFile
    };

    const formData = new FormData();
    for (const [key, value] of Object.entries(requestBody)) {
      console.log(key, value.toString());
      formData.append(key, value.toString());
    }
    formData.append('audioFile', selectedFile);
    return formData;
  };

  const uploadFormData = async (formData: FormData) => {
    return new Response(null, {
      status: 200,
      statusText: 'OK',
      headers: new Headers({
        'Content-Type': 'application/json', // Adjust content type if necessary
      }),
    });
    // await fetch(FILE_UPLOAD_API, {
    //   method: POST,
    //   body: formData,
    // });
  };

  const handleUploadSuccess = () => {
    console.log(SUCCESS_MSG);
    setUploadSuccess(true);
  };

  const handleUploadError = (error?: any) => {
    console.error(CONSOLE_ERROR_MSG, error);
  };

  const handleUploadButtonClick = () => {
    setUploadProgress(0);
    setUploadSuccess(false);
  };

  return {
    selectedFile,
    errorMessage,
    uploadProgress,
    uploadSuccess,
    handleFileChange,
    handleUpload,
    handleUploadButtonClick
  };
};