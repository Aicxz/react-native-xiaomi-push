
# react-native-xiaomi-push

## 安装
```
npm install @aicxz/react-native-xiaomi-push --save
```
or
```
yarn add @aicxz/react-native-xiaomi-push
```
## 

## 集成
#### android/app/src/main/AndroidManifest.xml 添加配置
```xml
<!-- 小米推送 -->
<application>
  <meta-data
    android:name="XIAOMI_APPID"
    android:value="appid=${XIAOMI_APPID}" />
  <meta-data
    android:name="XIAOMI_APPKEY"
    android:value="appkey=${XIAOMI_APPKEY}" />
</application>
```
#### android/app/build.gradle 设置申请的AppId和AppKey
```
android {
  defaultConfig {
    manifestPlaceholders = [
	  XIAOMI_APPID: "<app_id>",
	  XIAOMI_APPKEY: "<app_key>",
    ]
  }
}
```
## 使用
```javascript
import RNXiaomiPush from '@aicxz/react-native-xiaomi-push';
```
