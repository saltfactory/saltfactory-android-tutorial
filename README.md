## 블로그 글

이 예제는 [saltfactory's blog](http://blog.saltfactory.net)의 [최신 Android Studio, GCM(Google Cloud Messaging), Node.js를 이용하여 Android 푸시 서비스 구현하기](http://blog.saltfactory.net/android/implement-push-service-via-gcm.html) 글의 예제 소스 코드입니다.

이 소스코드는 **Android** 개발을 할 때 [GCM(Google Cloud Messaging)](https://developers.google.com/cloud-messaging/)을 이용하여 푸시 서비스를 **Android Studio**로 개발하는 내용을 포함하고 있습니다.

## 디렉토리 구조

* **sf-gcm-demo/** : Android Studio로 개발한 GCM Demo 앱 소스코드가 포함되어 있습니다.
* **node/** : Node.js로 만든 GCM을 이용하여 Android 디바이스로 메세지를 보내는 소스코드가 포함되어 있습니다.
* **bash/** : GCM 서비스의 Google Connection Server를 이용하여 Android 디바이스로 메세지를 보내는 소스코드가 포함되어 있습니다.


## 연구원 소개

* 작성자 : [송성광](http://about.me/saltfactory) 개발 연구원
* 블로그 : http://blog.saltfactory.net
* 이메일 : [saltfactory@gmail.com](mailto:saltfactory@gmail.com)
* 트위터 : [@saltfactory](https://twitter.com/saltfactory)
* 페이스북 : https://facebook.com/salthub
* 연구소 : [하이브레인넷](http://www.hibrain.net) 부설연구소
* 연구실 : [창원대학교 데이터베이스 연구실](http://dblab.changwon.ac.kr)


## 기부하기

> 기부금은 연구활동과 블로그 운영에 사용됩니다.

기부방법은 [PayPal](https://paypal.com)을 이용하는 방법이 있습니다.
**Donate(기부하기)** 버튼을 클릭하여 기부할 수 있습니다.

<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>


## 라이센스

The MIT License (MIT)

Copyright (c) 2014 SungKwang Song

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
