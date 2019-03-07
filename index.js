import {
    Platform,
    NativeModules,
    DeviceEventEmitter
} from 'react-native';

const { RNXiaomiPush } = NativeModules;

const listeners = {};

const receiveRegId = 'XiaomiPushReceiveRegId';


export default class XiaomiPush {

    /**
     * 初始化推送服务
     */
    static registerPush() {

        if (Platform.OS === 'android') {
            RNXiaomiPush.registerPush();
        }

    }

    /**
     * 关闭推送
     */
    static unregisterPush() {

        if (Platform.OS === 'android') {
            RNXiaomiPush.unregisterPush();
        }

    }

    /**
     * 暂停推送
     */
    static pausePush() {

        if (Platform.OS === 'android') {
            RNXiaomiPush.pausePush();
        }

    }

    /**
     * 恢复推送
     */
    static resumePush() {

        if (Platform.OS === 'android') {
            RNXiaomiPush.resumePush();
        }

    }

    /**
     * 获取RegId
     * @returns {Promise<{}>}
     */
    static async getRegId() {

        if (Platform.OS === 'android') {
            return await RNXiaomiPush.getRegId();
        }

    }


    /**
     * RegId下发事件
     */
    static addReceiveRegIdListener(cb) {

        if (Platform.OS === 'android') {

            listeners[receiveRegId] = DeviceEventEmitter.addListener(
                receiveRegId,
                args => cb(args)
            )

        }

    }

    /**
     * 移除RegId下发事件监听
     */
    static removeReceiveRegIdListener() {

        if (!listeners[receiveRegId]) {
            return;
        }

        listeners[receiveRegId].remove();
        listeners[receiveRegId] = null;

    }

}
