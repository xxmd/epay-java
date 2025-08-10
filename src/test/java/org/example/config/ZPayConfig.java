package org.example.config;

public class ZPayConfig implements PayPlatformConfig {
    @Override
    public String getHostname() {
        return "z-pay.cn";
    }

    @Override
    public String gePid() {
        return "2025071018032699";
    }

    @Override
    public String getKey() {
        return "kp5GG9c0HLPCmipoBH89e5INTPzR9IUO";
    }


    public enum ZPayChannel {
        ALI_PAY("9596");
        private String channelId;

        public String getChannelId() {
            return channelId;
        }

        ZPayChannel(String channelId) {
            this.channelId = channelId;
        }
    }
}
