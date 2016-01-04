package com.wallet.xlo.walletforandroid.model.config;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 16-1-4.
 * it's the protocol config
 */
public class ProtocolConfig {

    private volatile static ProtocolConfig protocolConfig;

    public static ProtocolConfig getProtocolConfig() {
        if (protocolConfig == null) {
            synchronized (ProtocolConfig.class) {
                if (protocolConfig == null) {
                    protocolConfig = new ProtocolConfig();
                }
            }
        }
        return protocolConfig;
    }

    protected List<PostInfo> postInfo;
    private Map<String, Map<String, Map<Class[], Method>>> method;
    private Map<String, Constructor<?>> manager;

    private ProtocolConfig() {
        this.postInfo = new LinkedList<>();
        this.method = new HashMap<>();
        this.manager = new HashMap<>();
    }

    public void loadConfig(XmlResourceParser xmlResourceParser) throws IOException, XmlPullParserException {
        xmlResourceParser.next();
        int eventType = xmlResourceParser.getEventType();
        PostInfo postInfo = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlResourceParser.getName().equals("solver")) {
                    postInfo = new PostInfo();
                    postInfo.name = xmlResourceParser.getAttributeValue(null, "name");
                    postInfo.url = xmlResourceParser.getAttributeValue(null, "url");
                    postInfo.manager = xmlResourceParser.getAttributeValue(null, "manager");
                } else if (xmlResourceParser.getName().equals("method")) {
                    assert postInfo != null;
                    postInfo.method = xmlResourceParser.getAttributeValue(null, "name");
                    postInfo.array = "array".equals(xmlResourceParser.getAttributeValue(null, "type"));
                    postInfo.data = "data".equals(xmlResourceParser.getAttributeValue(null, "type"));
                } else if (xmlResourceParser.getName().equals("data")) {
                    assert postInfo != null;
                    postInfo.methodData.add(xmlResourceParser.nextText());
                }
            } else if (eventType == XmlPullParser.END_TAG && xmlResourceParser.getName().equals("solver")) {
                this.postInfo.add(postInfo);
                postInfo = null;
            }
            eventType = xmlResourceParser.next();
        }
        this.method.clear();
        this.manager.clear();
    }

    public List<PostInfo> getPostInfo() {
        return postInfo;
    }

    public PostInfo findPostInfo(String url) {
        for (PostInfo now : this.postInfo) {
            if (now.getUrl().equals(url)) {
                return now;
            }
        }
        return null;
    }

    public Constructor<?> getManagerConstructor(String className) throws ClassNotFoundException, NoSuchMethodException {
        if (!this.manager.containsKey(className)) {
            Class[] paramTypes = {Socket.class};
            this.manager.put(className, Class.forName(className).getConstructor(paramTypes));
        }
        return this.manager.get(className);
    }

    public Method getMethod(String className, String methodName, Class[] methodParamType) throws ClassNotFoundException, NoSuchMethodException {
        if (!this.method.containsKey(className)) {
            this.method.put(className, new HashMap<String, Map<Class[], Method>>());
        }
        Map<String, Map<Class[], Method>> map = this.method.get(className);
        if (!map.containsKey(methodName)) {
            map.put(methodName, new HashMap<Class[], Method>());
        }
        Map<Class[], Method> methodMap = map.get(methodName);
        if (!methodMap.containsKey(methodParamType)) {
            methodMap.put(methodParamType, Class.forName(className).getMethod(methodName, methodParamType));
        }
        return methodMap.get(methodParamType);
    }

    public class PostInfo {
        private String name;
        private String url;
        private String manager;
        private String method;
        private boolean array, data;
        private List<String> methodData = new ArrayList<>();

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getManager() {
            return manager;
        }

        public String getMethod() {
            return method;
        }

        public boolean isArray() {
            return array;
        }

        public boolean isData() {
            return data;
        }

        public List<String> getMethodData() {
            return new ArrayList<>(methodData);
        }
    }
}
