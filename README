# FCM(Firebase Cloud Messasing)을 사용하여 푸시 발송하기 예제

## FCM (Firebase Cloud Messasing)

FCM은 GCM(Google Cloud Messasing)의 새로운 버전이다. Google이 Firebase를 인수한 이후 기존의 API를 Firebase의 PaaS 와 통합을 하는 작업을 진행하고 있었던 것 같다. GCM을 설명얼 찾기 위해서 Google API 문서를 찾기 어려울 뿐만 아니라, 기존의 링크를 열면 자동으로 FCM 소개가 나오게 된다. Google에서는 GCM에서 FCM으로 업그레이드하도록 유도하고 있다.
![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%206.10.43%20PM.png)
<이미지출처 : https://developers.google.com/cloud-messaging/ >

Firebase는 인기 있는 PaaS 플랫폼 서비스를 제공하고 하고 있었다. Google에서 Firebase를 소개하는 글을  https://developers-kr.googleblog.com/2016/05/firebase-8-firebase-google43.html 링크에서 참조하면 된다.

여하튼 Google은 새로운 클라우드 메세징 쉽게 말하면 푸시를 발송하는 방법을 GCM에서 FCM으로 변경하였다. FCM이 GCM과 완전 다른 개념은 아니라 포함된다고 보면 된다. 이 글에서는 FCM을 가지고 안드로이드 디바이스에 메세지를 보내는 방법을 소개한다.
간단한 메시지 전송을 소개하기 위해서 [PushServer]() 와 [PushApp]() 소스를 포함하고 있다.


## Firebase 프로젝트 생성

기존에는 Google API Console에서 API에 관련된 모든 것을 설정하였지만 FCM이 등장하고 Firebase 프로젝트로 따로 등록해서 사용하게 된다.

구글 계정으로 로그인한 뒤 https://console.firebase.google.com 으로 접속하면 Firebase의 콘솔로 접근할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.34.00%20AM.png)

FCM을 사용하기 위해서는 Firebase 프로젝트를 생성해야한다. **새프로젝트 생성** 버튼을 클릭하면 **프로젝트 이름**, **국가/지역** 을 입력하는 입력폼이 나온다. 프로젝트 이름이 앱의 이름과 같을 필요는 없다. Firebase의 하나의 프로젝트 안에서 여러개의 앱을 등록해서 관리할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.34.52%20AM.png)

## 안드로이드 앱 Firebase 추가

새로운 프로젝트를 생성하면 다음과 같은 화면이 나타난다. Firebase는 Android 앱 뿐만 아니라 iOS 또는 Web 앱을 개발할 때 같이 사용할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.35.14%20AM.png)

우리는 안드로이드 앱에 메세지를 전송하는 예제를 진행하기 때문에 **Android 앱 Firebase 추가** 버튼을 클릭한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.35.21%20AM.png)

예전에 GCM을 구현할 때 처음 GCM을 사용할 때 어디에서 어떻게 앱을 등록하고 필요한 id를 획득하는지 혼란스러운 부분이 많았다. 하지만 이번 업데이트에서 가장 좋은 부분은 개발자가 고민하지 않고 진행할 수 있다는 것이다. **Android 앱에 Firebase 추가**를 하면 이후부터 Twitter의 Fabric과 같은 인터페이스를 사용해서 필요한 코드를 쉽게 획득하고 첨부할 수 있다. 지금부터 그 과정을 살펴보자.

**패키지 이름** : 안드로이드 앱의 기본 패키지 이름과 동일하게 사용한다.   
**앱 닉네임** : 선택사항으로 앱의 별명을 입력할 수 있다.
**디버그 서명 인증서** : 선택사항으로 인증서의 SHA-1를 등록할 수 있다.

이 화면에서는 **패키지 이름** 만 정확하게 기입하면 된다. 

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.36.17%20AM.png)

FCM을 쉽게 구현하기 위해서는 [Android Studio](https://developer.android.com/studio/index.html)가 반드시 필요한데 **2.2** 이상 버전부터 IDE에서 바로 FCM을 구현할 수 있는 패널을 지원하고 있다. 혹시 Android Studio 버전이 2.2 이하를 사용하고 있다면 2.2 이상으로 사용하길 강력하게 추천한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/hero_image_studio_2-2_2x.png)
<이미지출처: https://developer.android.com/studio/index.html >

Firebase에 프로젝트를 생성하고 Android 앱을 추가했다면 이제 Android Studio에서 직접 FCM을 설정할 차례이다.

## Android 프로젝트 생성

Android Studio를 다운 받았다면 새로운 프로젝트를 생성한다. 프로젝트를 생성할 때는 Firebase에 등록한 **패키지 이름**과 동일한 패키지명으로 등록한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%206.58.06%20PM.png)

다음은 Android 디바이스의 SDK 버전을 선택한다. 오래된 안드로이드를 지원하기 위해서는 4.1 이상을 추천하지만 이 연습과정에서는 5.0을 사용한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%206.58.53%20PM.png)

마지막으로 기본 뷰를 정하는 Activity를 선택할 수 있는데 **Basic Activity**를 선택한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.00.32%20PM.png)

Activity의 이름은 그냥 기본(MainActivity)으로 둔다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.01.47%20PM.png)

Android 프로젝트 설정을 모두 완료하면 다음과 같은 화면이 나타난다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.03.24%20PM.png) 

## Android Studio 프로젝트에서 Firebase 툴 사용

Android Studio 2.2 이상부터는 Firebase 툴을 사용하여 FCM 설정을 손쉽게 할 수 있다. GCM 시절에는 Google API Console에서 여러 복잡한 과정을 거쳐서 필요한 ID 를 생성했지만 Android Studio 2.2 부터는 Firebase 툴이 모든것을 해결해준다.
Android Studio 메뉴에서 ** 도구(Tools) > Firebase ** 메뉴를 선택한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.41.47%20AM.png)

Firebase 메뉴를 클릭하면 Android Studio 안에 **Assistant** 가 열리게 된다. 우리는 Firebase의 기능중에 **Cloud Messaging** 을 사용할 것이다. **Set up Firebase Cloud Messaging** 링크를 클릭한다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.42.18%20AM.png)

FCM을 사용한다고 선택하게 되면 **Set up Firebase Cloud Messasing** 화면이 나타나게 된다. 아직은 Android Studio와 App과 Firebase의 연결이 없기 때문에 비활성화 되어있는 버튼들이 보인다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.42.35%20AM.png) 

FCM 셋업 방법은 매우 간단하다. Assistant에 나와 있는 숫자 순서대로 진행하면 된다.

### Connect your app to Firebase

Android Studio에서 개발하려고 하는 App과 Firebase를 연결하는 작업이다. **Connect to Firebase** 버튼을 클릭한다. 이 버튼을 클릭하면 구글 계정을 사용하여 Firebase에 연동하는 화면이 나타난다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.43.17%20AM.png)

구글 계정으로 Firebase 연동하는 화면에서 **Accept** 버튼을 선택하면 다음과 같이 Firebase와 연결이 완료된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.43.29%20AM.png)

연결 과정이 성공적으로 완료되면 Android Studio 에 다음과 같이 연결되었다고 상태가 변화게 된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.44.17%20AM.png) 

### Add FCM to your app

위에서 Android Studio와 Firebase를 연결했다면 이제는 Firebase에 등록한 프로젝트의 앱과 지금 개발하려고 하는 앱을 서로 연결하는 작업을 **Add FCM to your app** 버튼을 클릭하여 진행한다.
이 버튼을 클릭하면 Firebase에서 생성한 프로젝트를 선택하는 화면이 나타난다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%2011.43.52%20AM.png) 

Firebase의 프로젝트에 연결이되면 현재 Android Studio에 열려있는 프로젝트안에 **build.gradle**과 **app/build.gradle** 안에 FCM을 개발할 때 필요한 의존성 라이브러리를 자동으로 선언하는 문장이 추가된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%201.21.24%20PM.png)

필요한 라이브러는 **com.google.gms:google-serivces:3.0.0** 과 **com.google.gms.google-services** 그리고 **com.google.firebase:firebase-messaging:9.2.1** 이다. 라이브러리에서 보듯 FCM 이 GMS의 확장인것을 확인할 수 있다.

FCM 프로젝와 연결 작업이 끝나면 프로젝트의 의존성 문제를 자동으로 해결하게 된다. 설정이 끝나면 다음과 같이 상태가 변경된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%201.21.58%20PM.png)

실제 파일을 열어보면 의존성 라이브러리들이 추가된 것을 확인할 수 있다.

## Access the device registration token 

이제 우리가 직접 클래스를 생성할 차례이다. 가장먼저 생성하는 클래스는 [Android Services](https://developer.android.com/guide/components/services.html) 클래스의 하나로 실제 토큰을 등록하는 클래스이다. 이 클래스는 **FirebaseInstanceIDService**를 상송하여 만든다. 이때 이름은 **MyFirebaseInstanceIDService**라고 만들어 보도록 한다.
**onTokenRefresh()** 메소드를 오버라이드 하여 구현한다. 필요한 코드는 Android Studio의 Assestant에 나와 있다. 그대로 드래그해서 새롭게 추가한 MyFirebaseInstanceIDService 파일 안에 추가한다.

```java
package net.saltfactory.tutorial.fcmdemo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by saltfactory on 10/11/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        // 안드로이드 앱이 구동하면 실제 토큰을 획득하게 되는데 획득한 토큰을 푸시 서버로 전송하는 메소드를 추가 구현해야한다.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token){
        // 푸시서버로 token을 전송하는 코드 추가
    }
}

```

MyFirebaseInstanceIDService 클래스를 추가하고 난 뒤 **AndroidManifest.xml**에 방금 추가한 서비스를 등록한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="net.saltfactory.tutorial.fcmdemo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name"
            android:theme="@style/AppTheme.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- 토큰정보를 획득해서 푸서서버로 전송하는 서비스객체 -->
        <service android:name=".MyFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>
    </application>

</manifest>
```

이 설정을 마치고 Android Studio에 실제 물리적인 안드로이드 디바이스를 연결하고 실행하면 토큰 정보가 Android Studio 로그에서 확인할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.38.46%20PM.png)

디바이스를 Android Studio에 연결할 때는 안드로이드 디바이스 설정메뉴에 들어가서 **개발자모드**를 활성화하고 **USB 디버깅**을 활성화해야한다. 앱을 실행하면 디바이스 토큰을 확인할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.44.07%20PM.png)

### Handle messages

다음은 실제 안드로이드 단말기로 FCM으로 메세지를 받았을 때 메세지를 처리하기 위해 Android Services 클래스를 하나 더 추가한다. 이번에 클래스 이름은 **FirebaseMessagingService** 를 상속받아 **MyFirebaseMessagingService**으로 만든다.
실제 FCM이 도착하여 메세지를 받는 메소드가 **onMessageReceived** 로 이 메소드를 오버라이드 하여 메세지가 도착했을 때 행위를 구현하면 된다. 예제에서는 간단히 로그를 남기도록 했다.

```java
package net.saltfactory.tutorial.fcmdemo;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import static com.google.android.gms.internal.zzs.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // 실제 메세지를 받았을 때 처리하는 부분
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
}

```

앞에서 토큰을 획득하는 서비스 클래스를 만들고 난 뒤 **AndroidManifest.xml**에 등록하듯 이 서비스 클래스도 등록하도록 한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="net.saltfactory.tutorial.fcmdemo">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name"
            android:theme="@style/AppTheme.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- 토큰정보를 획득해서 푸서서버로 전송하는 서비스객체 -->
        <service android:name=".MyFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>

        <!-- 메세지를 받게 되면 처리하는 로직을 구현한 서비스객체 -->
        <service android:name=".MyFirebaseMessagingService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>
    </application>

</manifest>
``` 

### Firebase Console에서 테스트 FCM 전송하기

이제 Android 에 FCM 메세지가 제대로 전송이 되는지 확인해보자. 예전에는 실제 메세지를 받기 위해서 푸시 서버를 구현한 뒤에야 확인이 가능했지만 이젠 Firebase 에서 바로 메세지를 보낼 수 있게 되었다. Firebase console을 브라우저에서 열어보자.
Firebase의 Console에서 **Notifications**라는 메뉴를 선택한다. 여기서 실제 안드로이드 단말기로 메세지를 발송 할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%207.53.00%20PM.png)

**NEW MESSAGE** 버튼을 클릭한다. **Compose message** 창이 열리면 메세지를 입력하고 **Target user** 란에 **App**을 선택한다. 그리고 우리가 앞에서 등록한 앱 패키지명을 확인하고 선택한다.
**SEND MESSAGE** 버튼을 클릭하면 실제 메세지가 FCM으로 단말기로 전송이 된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/device-2016-11-10-200138.png) 


## SpringBoot로 간단한 푸시서버 구현하기

우리가 흔히 말하는 푸시서버는 PushProvier 이고 실제 푸시는 FCM이 담당하고 있다. 우리는 사용자의 단말기 정보를 서버에 저장해서 특정 시점에 푸시를 전송해달라고 GCM에 요청을 하는 작업을 할 수 있다.
이 때 필요한 것이 푸시 서버이다. 이번 예제에서는 Java를 이용하여 푸시서버를 구축한다.

IDE는 IntelliJ를 사용하였고, 웹 프레임워크는 SpringBoot의 MVC를 사용하고 데이터베이스는 SQLite를 사용하였다.
InteliiJ에서 가장 빠르게 SpringBoot 웹 프로젝트를 만드는 방법은 새로은 프로젝틀 **Spring Initializr**를 사용하여 생성하는것이다.
[IntelliJ에서 스프링 프레이워크 생성하기](http://blog.saltfactory.net/java/creating-springboot-project-in-intellij.html) 글에 자세하게 소개하고 있어 참고 URL로 프로젝트 생성하는 방법은 생략한다.

프로젝트가 생성이 되고 난 다음에는 의존성 라이브러리를 **build.gradle** 파일에 추가한다. 필요한 라이브러리는 다음과 같다.

** compile "org.springframework.boot:spring-boot-starter-jdbc" ** : 디바이스 토큰을 데이터베이스로 관리하기 위해 jdbcTemplate을 사용할 것이다.
** compile group: 'org.xerial', name: 'sqlite-jdbc', version: '3.8.11.2' ** : SQL 를 위한 jdbc 라이브러리가 필요하다.

```java
buildscript {
	ext {
		springBootVersion = '1.4.2.RELEASE'
	}
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
	}
}

apply plugin: 'java'
apply plugin: 'eclipse-wtp'
apply plugin: 'org.springframework.boot'
apply plugin: 'war'


war {
	baseName = 'pushserver'
	version = '0.0.1-SNAPSHOT'
}
sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
	mavenCentral()
}

configurations {
	providedRuntime
}

dependencies {
	compile('org.springframework.boot:spring-boot-starter-web')
	compile "org.springframework.boot:spring-boot-starter-jdbc"
	compile group: 'org.xerial', name: 'sqlite-jdbc', version: '3.8.11.2'
	providedRuntime('org.springframework.boot:spring-boot-starter-tomcat')
	testCompile('org.springframework.boot:spring-boot-starter-test')
}

``` 

gradle에 필요한 라이브러리를 추가한 다음 빌드하거나 새로고침을하게 되면 자동으로 의존성 라이브러리들을 다운받게 된다.

** Model 생성

이 에제에서 필요한 모델은 간단하다. 토큰 정보를 저장하는 Device와 메세제를 전송할 Message 가 전부이다. Message는 실제 내요을 담은 Notification 객체를 가진다.

```java
package net.hibrain.tutorial.pushserver.models;

/**
 * Created by saltfactory on 10/11/2016.
 */
public class Device {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

```

```java
package net.hibrain.tutorial.pushserver.models;

/**
 * Created by saltfactory on 10/11/2016.
 */
public class Notification {
    private String title;
    private String text;

    public Notification() {
    }

    public Notification(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

```

```java
package net.hibrain.tutorial.pushserver.models;


/**
 * Created by saltfactory on 10/11/2016.
 */
public class Message {
    private String to;
    private Notification notification;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

```

위 모델을 데이터베이스로 상호 작용을 하기 위해서 간단한 DAO 클래스를 만들었다. DeviceDao 클래스는 토큰을 포함한 디바이스정보를 저장하는 **saveDevice()**와 푸시 발송을 위해 모든 디바이스를 조회하는 **findAll()** 메소드로 구성되어 있다.

```java
package net.hibrain.tutorial.pushserver.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saltfactory on 10/11/2016.
 */
@Component
public class DeviceDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DeviceDao(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS  devices(id INTEGER PRIMARY KEY AUTOINCREMENT, token varchar(255))");

    }

    public List<Device> findAll() {
        List<Device> devices = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from devices");
        for (Map row : rows) {
            Device device = new Device();
            device.setToken((String) row.get("token"));
            devices.add(device);
        }

        return devices;
    }

    public int saveDevice(Device device) {
        return jdbcTemplate.update("insert into devices (token) values (?)", new Object[]{device.getToken()});

    }
}

```

SpringBoot에서 SQLite를 사용하여 JDBTemplate을 사용하기 위해서는 몇가지 설정이 필요하다. **application.properties**를 열어서 **jdbc** 설정을 추가해야한다.

```java
spring.datasource.url=jdbc:sqlite:test.db
spring.datasource.username=test
spring.datasource.password=test
spring.datasource.driver-class-name=org.sqlite.JDBC
```

다음은 SpringBoot 가 동작할 때 자등으로 JDBC 설정을 application.properties 를 참조해서 진행할 수 있도록 **@EnableAutoConfiguration** 어노테이션을 어플리케이션 클래스에 추가한다.

```java
package net.hibrain.tutorial.pushserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class PushServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PushServerApplication.class, args);

	}
}
```

### 토큰저장과 메세지 전송 컨트롤러 

마지막으로 안드로이드 디바이스에서 토큰정보를 저장하고 메세제를 발송하는 컨트롤러를 추가한다.
이 예제의 핵심은 크게 두가지 이다. **디바이스트 토큰**을 서버에 저장시키는 것과, 저장된 디바이스 토큰으로 안드로이드 단말기로 FCM  전송을 요청하는 것이다.
토큰을 저장하는 url은 http://localhost:8080/tokens/save 이고 메세지를 발송하는 url은 http://localhost:8080/messages/send 이다.

토큰을 저장하는 메소드는 안드로이드 디바이스에서 JSON으로 다음 내용을 전송하면 자동으로 데이터베이스에 저장하는 역활을 한다. 위에서 Android Studio에서 **MyFirebaseInstanceIDService** 구현한 것을 기억하는가? 이 때 획득한 안드로이드 디바이스의 토큰정보를 JSON 형태로 만들어서 서버로 전송하면 된다.

```javascript
{
    "token":"dCC37T7zLX4:APA91bGZakVgC .....생략"
}
```

이렇게 안드로이드 단말기에서 전송된 JSON은 SpringBoot의 Controller에서 Device 객체로 매핑이 되어 DeviceDao를 거쳐 실제 SQLite 데이터베이스로 저장이된다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%208.31.52%20PM.png)

푸시 서버에서 다음으로 가장 중요한 역활은 실제 안드로이드 디바이스로 메세지를 전송하는 것이다. 우리는 사용자의 디바이스 토큰 정보를 데이터베이스에 저장하고 있다. 이제 메세제를 지정해서 전송하고 싶은 디바이스 토큰을 조회해서 FCM으로 푸시를 요청하면 된다.
GCM까지는 푸시를 요청하기 위해서 특별한 라이브러리가 필요했지만 FCM에서 FCM Http Connection을 지워하기 때문에 우리가 쉽게 사용하는 REST API 처럼 FCM을 요청할 수 있게 되었다.
Spring Boot MVC에서 기본적으로 가지고 있는 **RestTemplate**을 사용하여 쉽게 HTTP 요청을 구현한다.

FCM Http Connection 의 가장 핵심은 Header 정보에 **Authorization**으로 **SERVER_EKY**를 보내는 것이다. 이 때 **SERVER_KEY**는 앞에서 **Firebase 프로젝트**를 생성하면 자동으로 만들어지는 서버인증키이다.
Firebase Console에 접속하여 프로젝트를 선택한 후 **Project Settings** 라는 메뉴를 선택한다. 그리고 **Cloud Message** 탭을 열면 **Project Credentials**로 **Server key**와 **Sender ID**를 확인할 수 있다.
![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen_Shot_2016-11-10_at_8_37_13_PM.png)

이 값을 application.properties 안에 추가한다. 그리고 FCM HTTP Connection 을 요청하는 endpoint URL을 다음과 같이 함께 저장한다. 이 두 값은 Controller에서 Injection 하여 사용할 것이다.

```java
SERVER_KEY=AIzaSyD...생략
GCM_SERVER_URL=https://gcm-http.googleapis.com/gcm/send

```


```java
package net.hibrain.tutorial.pushserver.controllers;

import net.hibrain.tutorial.pushserver.models.Device;
import net.hibrain.tutorial.pushserver.models.DeviceDao;
import net.hibrain.tutorial.pushserver.models.Message;
import net.hibrain.tutorial.pushserver.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saltfactory on 10/11/2016.
 */
@RestController
public class MainController {

    /**
     * Firebase 프로젝트의 Server Key
     */
    @Value("${SERVER_KEY}")
    private String SERVER_KEY;

    /**
     * Firebase의 FCM Http connection URL
     */
    @Value("${GCM_SERVER_URL}")
    private String GCM_SERVER_URL;


    @Autowired
    private DeviceDao deviceDao;

    /**
     * 안드로이드로 부터 토큰정보를 저장하는 메소드
     * @param device
     * @return
     */
    @RequestMapping(value = "/tokens/save", method = RequestMethod.POST)
    @ResponseBody
    int saveToken(@RequestBody Device device) {
        return deviceDao.saveDevice(device);
    }

    /**
     * 데이터베이스에 가지고 있는 모든 디바이스 토큰을 대상으로 메세지를 보내는 메소드 
     * @return
     */
    @RequestMapping("/messages/send")
    @ResponseBody
    List sendMessage() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Authorization", "key=" + SERVER_KEY);

        RestTemplate restTemplate = new RestTemplate();
        List<String> results = new ArrayList<String>();
        List<Device> devices = deviceDao.findAll();

        for (Device device : devices) {
            Message message = new Message();
            message.setTo(device.getToken());
            message.setNotification(new Notification("테스트메세지", "테스트메세지내용"));

            HttpEntity<Message> entity = new HttpEntity<Message>(message, headers);
            String result = restTemplate.postForObject(GCM_SERVER_URL, entity, String.class);
            results.add(result);
        }

        return results;
    }
}

```

이제 푸시 서버의 모든 설정이 끝났다. 실제 안드로이드의 토큰을 획득하는 코드에 다음과 서버로 전송하는 코드를 추가하자

```java
package net.saltfactory.tutorial.fcmdemo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by saltfactory on 10/11/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        // 안드로이드 앱이 구동하면 실제 토큰을 획득하게 되는데 획득한 토큰을 푸시 서버로 전송하는 메소드를 추가 구현해야한다.
        try {
            sendRegistrationToServer(refreshedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) throws JSONException, IOException {
        // 푸시서버로 token을 전송하는 코드 추가
        HttpURLConnection conn    = null;

        OutputStream os   = null;
        InputStream is   = null;
        ByteArrayOutputStream baos = null;

        URL url = new URL("http://푸시서버주소:8080/tokens/save");
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        JSONObject json = new JSONObject();
        json.put("token", token);

        os = conn.getOutputStream();
        os.write(json.toString().getBytes());
        os.flush();

        String response;

        int responseCode = conn.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {

            is = conn.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            byte[] byteData = null;
            int nLength = 0;
            while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            byteData = baos.toByteArray();

            response = new String(byteData);

            Log.i(TAG, "DATA response = " + response);
        }
    }
}

```

이제 푸시 서버를 실행하고 앱을 실행시켜보자. 푸시 서버 데이터베이스 방금 실행한 안드로이드 디바이스 토큰이 하나 더 추가 된 것을 확인할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%208.51.34%20PM.png)

마지막으로 서버에서 FCM으로 안드로이드 디바이스로 메시지를 푸시하기 위해서 FCM을 요청을 해보자.
브라우저를 열어서 http://localhost:8080/message/send 를 입력해보자. 

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/Screen%20Shot%202016-11-10%20at%208.56.18%20PM.png)

결과를 보면 하나의 토큰은 전송이 되지 않았다. 그리고 하나의 토큰에는 전송이 되었다. 전송실패한 토큰은 더이상 존재하지 않는 토큰이기 때문에 이런 에러가 리턴되면 데이터베이스에서 토큰을 삭제하는 로직이 추가되어야한다. 
전송이 성공한 안드로이드 단말기는 다음과 같이 메세지가 정상적으로 FCM으로 전송되어진 것을 확인할 수 있다.

![](http://hbn-blog-assets.s3.amazonaws.com/saltfactory/images/device-2016-11-10-175813.png) 


# 참고 

- https://developers.google.com/cloud-messaging/http
- http://docs.spring.io/autorepo/docs/spring-android/1.0.x/reference/html/rest-template.html
- http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html
- http://stackoverflow.com/questions/25420284/spring-boot-cannot-load-jdbc-driver-class-org-sqlite-jdbc
- http://stunstun.tistory.com/248
- https://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
- https://firebase.google.com/support/faq/#gcm-fcm
- https://developers.google.com/cloud-messaging/
- https://developers-kr.googleblog.com/2016/05/firebase-8-firebase-google43.html
- https://developer.android.com/studio/index.html