package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.File;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.enums.file.FileTypeEnum;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
@Transactional
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @MockBean
    private CloudinaryService cloudinaryService;

    @Before
    public void setUp() throws IOException {
        Mockito.when(cloudinaryService.uploadFile(Mockito.any(MultipartFile.class)))
                .thenReturn("https://saveursolidaire/test.jpg");
    }

    @Test
    public void testSaveImage() {
        File file = fileService.saveImage(getMultipartFile());

        Assert.assertNotNull(file);
        Assert.assertEquals("test.jpg", file.getName());
        Assert.assertEquals("https://saveursolidaire/test.jpg", file.getUrl());
        Assert.assertEquals(FileTypeEnum.IMAGE, file.getType());
    }

    @Test
    public void testFormatFileImage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FileService fileService = new FileService();
        Method formatFile = FileService.class.getDeclaredMethod("formatFile", MultipartFile.class, FileTypeEnum.class);
        formatFile.setAccessible(true);

        File file = (File) formatFile.invoke(fileService, getMultipartFile(), FileTypeEnum.IMAGE);

        Assert.assertNotNull(file);
        Assert.assertEquals("test.jpg", file.getName());
        Assert.assertEquals(FileTypeEnum.IMAGE, file.getType());
    }

    @Test
    public void testFormatFileVideo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FileService fileService = new FileService();
        Method formatFile = FileService.class.getDeclaredMethod("formatFile", MultipartFile.class, FileTypeEnum.class);
        formatFile.setAccessible(true);

        File file = (File) formatFile.invoke(fileService, getMultipartFile(), FileTypeEnum.VIDEO);

        Assert.assertNotNull(file);
        Assert.assertEquals("test.jpg", file.getName());
        Assert.assertEquals(FileTypeEnum.VIDEO, file.getType());
    }

    private MockMultipartFile getMultipartFile() {
        return new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test".getBytes()
        );
    }
}
