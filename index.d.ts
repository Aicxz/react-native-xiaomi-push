export default class XiaomiPush {
    /**
     * 初始化推送服务
     */
    static registerPush(): void;
  
    /**
     * 关闭推送
     */
    static unregisterPush(): void;
  
    /**
     * 暂停推送
     */
    static pausePush(): void;
  
    /**
     * 恢复推送
     */
    static resumePush(): void;
  
    /**
     * 获取RegId
     * @returns {Promise<any>}
     */
    static getRegId(): Promise<any>;
  
    /**
     * RegId下发事件
     */
    static addReceiveRegIdListener(cb: Function): void;
  
    /**
     * 移除RegId下发事件监听
     */
    static removeReceiveRegIdListener(): void;
  }
  