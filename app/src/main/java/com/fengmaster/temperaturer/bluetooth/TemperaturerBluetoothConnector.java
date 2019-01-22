package com.fengmaster.temperaturer.bluetooth;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fengmaster.temperaturer.entry.QueryResponse;
import com.fengmaster.temperaturer.entry.SetParmsRequest;
import com.fengmaster.temperaturer.event.BluetoothOriginalMessage;
import com.fengmaster.temperaturer.service.BluetoothLeService;
import com.fengmaster.temperaturer.util.JSONUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class TemperaturerBluetoothConnector {


    /**
     * 蓝牙信息暂存容器
     */
    private StringBuffer bluetoothDataBuffer=new StringBuffer();

    private static final String O_QUERY_PARMS_MESSAGE="{\"T\":\"C\"}";


    public void queryParms(){
        BluetoothHelper.getInstance().sendString(O_QUERY_PARMS_MESSAGE);
    }


    public void setParms(SetParmsRequest setParmsRequest){
        new Thread(() -> {
            List<String> strPacks = setParmsRequest.getStrPacks(null);
            for (String s : strPacks) {
                BluetoothHelper.getInstance().sendString(s);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }




    /**
     * 蓝牙原始信息
     * @param originalMessage
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void bluetoothMsg(BluetoothOriginalMessage originalMessage){
        switch (originalMessage.getMessage()){
            case BluetoothLeService.ACTION_DATA_AVAILABLE:
                //蓝牙发送数据过来
                //由于是拆包发送的,需要组合才能读取到完整信息
                bluetoothDataBuffer.append(originalMessage.getData());
                tryDecodeBuffer(bluetoothDataBuffer);
                break;
        }

    }

    private static int packIndex=0;

    /**
     * 尝试解码已经收到的信息
     * @return 是否是完整的json
     */
    private boolean tryDecodeBuffer(StringBuffer buffer){
        packIndex++;
        if (packIndex%32==0){
            if (JSONUtil.isJSON(buffer.toString())){
                //是完整json
                String jsonStr=buffer.toString();
                buffer.setLength(0);//清空

                QueryResponse queryResponse = JSONObject.parseObject(jsonStr, QueryResponse.class);

                if (queryResponse.getType()!=null){
                    //解析为参数对象
                    EventBus.getDefault().post(queryResponse);
                }else {


                }


            }else {
                buffer.setLength(0);//清空
            }

        }
        return false;
    }



    private static TemperaturerBluetoothConnector instance;

    public static TemperaturerBluetoothConnector getInstance(){
        if (instance==null){
            instance=new TemperaturerBluetoothConnector();
        }
        return instance;
    }

    private TemperaturerBluetoothConnector() {
        EventBus.getDefault().register(this);
    }



}
