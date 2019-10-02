package com.huasun.targetscore.rabbitmq;

import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * Base class for objects that connect to a RabbitMQ Broker
 */
public abstract class IConnectToRabbitMQ {
	public String mServer;//server地址
    public String mExchange;//exchange名
    public int mPort;
    public String mUsername;
    public String mPassWord;

    protected Channel mModel = null;//channel
    protected Connection mConnection;

    protected boolean Running ;

    protected String MyExchangeType ;

    /**
     *
     * @param server The server address
     * @param exchange The named exchange
     * @param exchangeType The exchange type name
     */
    public IConnectToRabbitMQ(String server, String exchange, String exchangeType,int port,String username,String password)
    {
  	  mServer = server;
  	  mExchange = exchange;
      MyExchangeType = exchangeType;
      mPort=port;
      mUsername=username;
      mPassWord=password;
    }

    public void Dispose()
    {
        Running = false;

			try {
				if (mConnection!=null)
					mConnection.close();
				if (mModel != null)
					mModel.abort();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }

    /**
     * Connect to the broker and create the exchange
     * @return success
     */
    public boolean connectToRabbitMQ()
    {
  	  if(mModel!= null && mModel.isOpen() )//already declared
  		  return true;
        try
        {
      	  ConnectionFactory connectionFactory = new ConnectionFactory();
          connectionFactory.setHost(mServer);
          connectionFactory.setUsername(mUsername);
          connectionFactory.setPassword(mPassWord);
          connectionFactory.setPort(mPort);
          mConnection = connectionFactory.newConnection();
          //创建一个通道
          mModel = mConnection.createChannel();
          mModel.basicQos(1);
          //创建一个的交换器
          mModel.exchangeDeclare(mExchange, MyExchangeType, true);
          return true;
        }
        catch (Exception e)
        {
      	  e.printStackTrace();
            return false;
        }
    }
}