
# react-native-xiaomi-push

## Getting started

`$ npm install react-native-xiaomi-push --save`

### Mostly automatic installation

`$ react-native link react-native-xiaomi-push`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.aicxz.xiaomipush.XiaomiPushPackage;` to the imports at the top of the file
  - Add `new XiaomiPushPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-xiaomi-push'
  	project(':react-native-xiaomi-push').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-xiaomi-push/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-xiaomi-push')
  	```


## Usage
```javascript
import RNXiaomiPush from 'react-native-xiaomi-push';

// TODO: What to do with the module?
RNXiaomiPush;
```
