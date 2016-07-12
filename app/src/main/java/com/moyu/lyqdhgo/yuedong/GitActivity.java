package com.moyu.lyqdhgo.yuedong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lyqdhgo on 2016/7/11.
 */

public class GitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text();
    }

    private void text() {
        System.out.print("Git first commit");
    }

    private void test2(){
        System.out.print("qdh create mothod")
    }
  
    private void test3(){
        System.out.print("qdh master create mothod")
    }
    
    private void test4(){
	System.out.print("GitTest2 in branch devTest create test4 method")
    }
}
