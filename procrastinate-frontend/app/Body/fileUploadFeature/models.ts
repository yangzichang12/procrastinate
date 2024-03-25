const allowedFileTypes = [
    '.pdf', '.doc', '.docx', '.mp4', '.mp3', '.wav', '.m4a', // File extensions
    'application/pdf', 'application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', // MIME types
    'audio/mpeg', 'audio/wav', 'audio/x-m4a' // Additional audio MIME types
  ] as const;

export type AllowedFileType = typeof allowedFileTypes[number];
export const allFileTypes: AllowedFileType[] = [...allowedFileTypes];
  