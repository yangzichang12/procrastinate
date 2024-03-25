
import styled, {css} from 'styled-components';

export const FileInput = styled.input`
  display: none; /* Hide the default file input */
`;

export const UploadButton = styled.label`
  background-color: #8080D7; /* Example button color */
  color: #fff; /* Example text color */
  padding: 0.5rem 1rem;
  cursor: pointer;
  border-radius: 4px;
`;

export const StartUploadButton = styled.label`
  background-color: #8080D7; /* Example button color */
  color: #fff; /* Example text color */
  padding: 0.5rem 1rem;
  cursor: pointer;
  border-radius: 4px;
`;

export const FileName = styled.span`
  margin-left: 1rem;
`;

// export const CatProgressBarContainer = styled.div`
//   width: 100%;
//   height: 50px;
//   background-color: #f0f0f0;
//   border-radius: 4px;
//   margin-top: 0.5rem;
//   overflow: hidden;
//   ${props =>
//     props.uploadsuccess &&
//     css`
//       display: none;
//     `}
// `;

// export const CatProgressBar = styled.div`
//   width: 100%;
//   height: 100%;
//   background-image: url(${catImage});
//   background-size: cover;
//   clipPath: inset(0 ${props => 100 - props.progress}% 0 0);
// `;

export const SuccessMessage = styled.div`
  color: green;
  margin-top: 0.5rem;
`;

export const AllowedFileTypes = [
  'application/pdf',
  'application/msword',
  'video/mp4',
  'audio/mpeg',
  'audio/wav',
  'audio/x-m4a',
];