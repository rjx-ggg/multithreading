package com.woniu.anno;

import com.woniu.SayHiAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({SayHiAutoConfiguration.class})
public @interface EnableSayHi {
}
