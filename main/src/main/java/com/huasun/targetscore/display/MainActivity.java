package com.huasun.targetscore.display;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bcsb.rabbitmq.entity.Command;
import com.huasun.core.activities.ProxyActivity;
import com.huasun.core.app.ConfigKeys;
import com.huasun.core.app.Latte;
import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.util.log.LatteLogger;
import com.huasun.display.launcher.LauncherDelegate;
import com.huasun.display.sign.ISignListener;
import com.huasun.display.sign.SignInBottomDelegate;
import com.huasun.display.sign.SignInByFace.SignByFaceRecDelegate;
import com.huasun.display.sign.SignInByPassword.SignInByPassDelegate;
import com.huasun.targetscore.rabbitmq.MessageConsumer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends ProxyActivity implements ISignListener,ILauncherListener{
    //Activity是否已经收到了服务器端就绪的命令，如果Activity收到了该命令并根据传入的参数(0:密码登陆，1：脸部识别登陆,2:等候中)进入相应界面
    private int status=Latte.getConfiguration(ConfigKeys.COMMAND);
    private MessageConsumer mConsumer;
    private String server="192.168.1.3";
    private String queue_name = "signin-queue";
    private String exchange_name = "bcsb-exchange";
    private String exchange_type="topic";
    private int port=5672;
    private String username="client";
    private String password="client";
    private SignInBottomDelegate signInBottomDelegate=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        //开始消息队列
        // Create the consumer

        mConsumer = new MessageConsumer(server, exchange_name, exchange_type,port,username,password);
        new consumerconnect().execute();
        mConsumer.setOnReceiveMessageHandler(new MessageConsumer.OnReceiveMessageHandler() {

            public void onReceiveMessage(byte[] message) {
                Command command=null;
                String text = "";
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream (message);
                    ObjectInputStream ois = new ObjectInputStream (bis);
                    command = (Command) ois.readObject();
                    ois.close();
                    bis.close();
                    status=command.getIndex();
                    //这里的status为消息队列传递的command的index，SignInBottomeDelegate会直接读取ｉｎｄｅｘ，来决定启动哪个界面
                    // signin_by_pass("密码登陆",0),signin_by_face("人脸登陆",1),waiting("等候中",2),shoot("射击",3),finish("结束",4);
                    Latte.getConfigurator().withCommand(status);
                    //根据获得的命令显示或隐藏对应的Fragment
                    signInBottomDelegate.showHideFragment(signInBottomDelegate.getITEM_DELEGATES().get(1),signInBottomDelegate.getITEM_DELEGATES().get(2));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public LatteDelegate setRootDelegate() {
        //return new LauncherDelegate();
        this.signInBottomDelegate=new SignInBottomDelegate();
        return this.signInBottomDelegate;
        //刚开始为等待下命令状态

        //return SignInBottomDelegate.newInstance(Command.waiting.getIndex());
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登陆成功",Toast.LENGTH_LONG).show();
        //startWithPop(new BcsbBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        LatteLogger.e("denglu"," dengluchengong");
        Toast.makeText(this,"登陆成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInError(String msg){

    }

    @Override
    public void onSignUpError(String msg) {

    }

    @Override
    public void onSignInFailure(String msg) {

    }

    @Override
    public void onSignUpFailure(String msg) {

    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNIN_BY_PASS:
                Toast.makeText(this,"启动结束，用户将以密码登陆",Toast.LENGTH_LONG).show();
                start(new SignInBottomDelegate());
                break;
            case SIGNIN_BY_FACE:

                Toast.makeText(this,"启动结束，用户将以人脸识别方式登陆",Toast.LENGTH_LONG).show();
                start(new SignInBottomDelegate());
                break;
        }


    }

    @Override
    public int getStatus() {
        return Latte.getConfiguration(ConfigKeys.COMMAND);
    }

    //消息队列相关函数
    private  class consumerconnect extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... Message) {
            try {
                // Connect to broker
                mConsumer.connectToRabbitMQ();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            // TODO Auto-generated method stub
            return null;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        new consumerconnect().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConsumer.dispose();
    }

}
/*
public class MainActivity extends AppCompatActivity {
    ConnectionFactory factory = new ConnectionFactory();
    Thread subscribeThread;
    private MessageConsumer mConsumer;
    private String server="192.168.1.3";
    private String queue_name = "signin-queue";
    private String exchange_name = "bcsb-exchange";
    private String exchange_type="topic";
    private int port=5672;
    private String username="client";
    private String password="client";
    private String message = "";
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConsumer = new MessageConsumer(server, exchange_name, exchange_type,port,username,password);
        new consumerconnect().execute();
        mConsumer.setOnReceiveMessageHandler(new MessageConsumer.OnReceiveMessageHandler() {

            public void onReceiveMessage(byte[] message) {
                Toast.makeText(Latte.getApplicationContext(),message.toString(),Toast.LENGTH_LONG).show();
                TextView tv = (TextView) findViewById(R.id.textview);
                Date now = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                tv.append(ft.format(now) + ' ' + message.toString() + '\n');
            }
        });
    }
    //消息队列相关函数
    private  class consumerconnect extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... Message) {
            try {
                // Connect to broker
                mConsumer.connectToRabbitMQ();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            // TODO Auto-generated method stub
            return null;
        }

    }
}*/