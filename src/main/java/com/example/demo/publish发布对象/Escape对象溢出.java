package com.example.demo.publish发布对象;


import com.example.demo.annotations.NotRecommend;
import com.example.demo.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape对象溢出 {
    
    private int thisCanBeEscape = 0;

    public Escape对象溢出() {
        new InnerClass();
    }
    
    private class InnerClass{
        public InnerClass(){
            log.info("{}", Escape对象溢出.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape对象溢出();
    }
}
