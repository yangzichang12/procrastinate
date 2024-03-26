import React from 'react';
import { FileInput, UploadButton, FileName, SuccessMessage, StartUploadButton, DragDropArea } from './style';
import { SELECTED_FILE, BTN_START_UPLOAD, SUCCESS_MSG } from './strings';
import { useFileUploadLogic } from './fileUploadLogic';

const FileUploadContainer: React.FC = () => {
  const {
    selectedFile,
    errorMessage,
    uploadSuccess,
    handleFileChange,
    handleUpload,
    handleUploadButtonClick,
    handleFileDrop,
  } = useFileUploadLogic();

  return (
    <div>
      <UploadButton htmlFor="fileInput" onClick={handleUploadButtonClick}>Upload File</UploadButton>
      <DragDropArea
        onDragOver={(e: { preventDefault: () => any; }) => e.preventDefault()}
        onDrop={handleFileDrop}
      />
        <UploadButton>Drag & Drop File</UploadButton>
      <FileInput
        type="file"
        id="fileInput"
        accept=".pdf,.doc,.docx,.mp4,.mp3,.wav,.m4a"
        onChange={handleFileChange}
      />
      {selectedFile && <FileName>{SELECTED_FILE}{selectedFile.name}</FileName>}
      {errorMessage && <div>{errorMessage}</div>}
      {selectedFile && <StartUploadButton onClick={handleUpload}>{BTN_START_UPLOAD}</StartUploadButton>}
      {uploadSuccess && <SuccessMessage>{SUCCESS_MSG}</SuccessMessage>}
    </div>
  );
};

export default FileUploadContainer;
