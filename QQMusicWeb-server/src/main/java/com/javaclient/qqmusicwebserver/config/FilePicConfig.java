package com.javaclient.qqmusicwebserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
*定位各种文件，头像，图片的地址
* 会自动加载，然后写的这个支持该路径的Configurer会替换掉默认的，也就是重定向图片地址
* */
@Configuration
public class FilePicConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
/*   原理：通过addResourceHandler判断前端路径如果是/img/singerPic/ 中的所有的文件
*    都会通过addResourceLocations转换成，这边后端的img文件夹下的singerPic中的文件
* */

//        歌手的头像地址
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
        );

//        歌曲的logo图片地址
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator")
        );
//        歌曲地址
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") +
                "song" + System.getProperty("file.separator")
        );
//        用户头像地址
        registry.addResourceHandler("/avatorImages/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") +
                        "avatorImages" + System.getProperty("file.separator")
        );

//        歌单的封面图片地址
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator")
        );

    }
}
