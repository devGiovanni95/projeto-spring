package br.com.projetospring.services;

import br.com.projetospring.exceptions.FileException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {
    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    /*URI para retornar o endereço da imagem*/
    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream  inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentType);
        } catch (IOException ioException) {
            throw new FileException("Erro de IO: " + ioException.getMessage());
        }


    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            LOG.info("Iniciando upload");
            s3client.putObject(bucketName, fileName, inputStream, metadata);
            LOG.info("Upload finalizado");
            return s3client.getUrl(bucketName, fileName)/*Retornar a url*/.toURI()/*voltar uma uri*/;
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }

//    catch(
//    AmazonServiceException exception)
//
//    {
//        LOG.info("AmazonServiceException: " + exception.getErrorMessage());
//        LOG.info("Status code: " + exception.getErrorCode());
//    }catch(
//    AmazonClientException exception)
//
//    {
//        LOG.info("AmazonClientException: " + exception.getMessage());
//    }
}


