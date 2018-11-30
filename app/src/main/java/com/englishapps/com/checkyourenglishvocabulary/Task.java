package com.englishapps.com.checkyourenglishvocabulary;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;


public class Task {


    public Level level1 = new Level();


    public Level level2 = new Level();

    public Level level3 = new Level();

    public boolean bitis = false;

    public int skor;
}
