package br.com.fatecmogidascruzes.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class FileDTO {

	private String fileName;
	private String contentType;
	private long contentLength;
	private byte[] bytes;

}
