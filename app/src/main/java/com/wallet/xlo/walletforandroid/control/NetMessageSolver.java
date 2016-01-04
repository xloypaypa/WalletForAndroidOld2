package com.wallet.xlo.walletforandroid.control;

import com.wallet.xlo.walletforandroid.model.config.ProtocolConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xlo on 16-1-4.
 * it's the net message solver
 */
public class NetMessageSolver {

    private volatile static NetMessageSolver netMessageSolver;

    public static NetMessageSolver getNetMessageSolver() {
        if (netMessageSolver == null) {
            synchronized (NetMessageSolver.class) {
                if (netMessageSolver == null) {
                    netMessageSolver = new NetMessageSolver();
                }
            }
        }
        return netMessageSolver;
    }

    public void sendEvent(String command, byte[] message, Socket socket) throws Exception {
        ProtocolConfig config = ProtocolConfig.getProtocolConfig();
        ProtocolConfig.PostInfo postInfo = config.findPostInfo(command);

        Object manager = buildManager(config, postInfo, socket);
        Method method = getMethod(config, postInfo);
        Object[] data = getData(postInfo, message);
        method.invoke(manager, data);
    }

    private Object[] getData(ProtocolConfig.PostInfo postInfo, byte[] message) throws JSONException {
        if (postInfo.isArray()) {
            JSONArray jsonArray = new JSONArray(new String(message));
            List<Map<String, String>> list = new LinkedList<>();
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                Map<String, String> map = new HashMap<>();
                for (String title : postInfo.getMethodData()) {
                    map.put(title, object.getString(title));
                }
                list.add(map);
            }
            Object[] result = new Object[1];
            result[0] = list;
            return result;
        } else if (postInfo.isData()) {
            Object[] result = new Object[1];
            result[0] = message;
            return result;
        } else {
            JSONObject jsonObject = new JSONObject(new String(message));
            String[] data = new String[postInfo.getMethodData().size()];
            for (int i = 0; i < data.length; i++) {
                String title = postInfo.getMethodData().get(i);
                data[i] = jsonObject.getString(title);
            }
            return data;
        }
    }

    private Method getMethod(ProtocolConfig config, ProtocolConfig.PostInfo postInfo) throws ClassNotFoundException, NoSuchMethodException {
        if (postInfo.isArray()) {
            Class[] param = {List.class};
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        } else if (postInfo.isData()) {
            Class[] param = {byte[].class};
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        } else {
            Class[] param = new Class[postInfo.getMethodData().size()];
            for (int i = 0; i < param.length; i++) {
                param[i] = String.class;
            }
            return config.getMethod(postInfo.getManager(), postInfo.getMethod(), param);
        }
    }

    private Object buildManager(ProtocolConfig config, ProtocolConfig.PostInfo postInfo, Socket socket) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = config.getManagerConstructor(postInfo.getManager());
        return constructor.newInstance(socket);
    }


}
