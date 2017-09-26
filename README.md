(https://developer.android.com/guide/topics/providers/document-provider.html)
(https://developer.android.com/training/secure-file-sharing/setup-sharing.html)
##### Content providers
콘텐츠 제공자는 외부 애플리케이션에 데이터를 표시하며, 이때 데이터는 관계형 데이터베이스(RDB)에서 찾을 수 있는 테이블과 유사한 하나 이상의 테이블로서 표시됩니다. 한 행은 제공자가 수집하는 어떤 유형의 데이터 인스턴스를 나타내며, 행 안의 각 열은 인스턴스에 대해 수집된 개별적인 데이터를 나타냅니다.

앱에서 다른 앱으로 접근하기 위해서는 Client 앱은 Content Resolver를 가지고 있어야 하고 Server 앱은 Content Provider를 가지고 있어야 한다. 서버 앱은 Manifest에 다음과 같은 tag을 가지고 있어야 한다.
```
<provider   android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false" />
```

URI라는 것은 다른 앱의 source 접근 하기 위해 필요하다.
URI - Uniform Resource Identifirer
In information technology, a Uniform Resource Identifier (URI) is a string of characters used to identify a resource. (위키피디아)

사용자 사전 제공자로부터 단어와 그에 해당하는 로케일 목록을 가져오려면, ContentResolver.query()를 호출하면 됩니다. query() 메서드는 사용자 사전 제공자가 정의한 ContentProvider.query() 메서드를 호출합니다. 다음 몇 줄의 코드는 ContentResolver.query() 호출을 보여줍니다.
```
// Queries the user dictionary and returns results
mCursor = getContentResolver().query(
    UserDictionary.Words.CONTENT_URI,   // The content URI of the words table
    mProjection,                        // The columns to return for each row
    mSelectionClause                    // Selection criteria
    mSelectionArgs,                     // Selection criteria
    mSortOrder);                        // The sort order for the returned rows
```

Contact앱에 있는 내용
```java
ContentResolver resolver = getContentResolver();
Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 전화번호 URI
String projection[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        }; //각 행의 이름
Cursor cursor = resolver.query(uri, projection, null, null, null); // 쿼리를 날려 받은 결과를 Cursor 클래스로 처리한다. 마치 데이터베이스 할 때 쿼리로 얻은 정보를 Cursor 처리 하는 것과 같음
```
Cursor는 DB에 원하는 데이터를 질의한 결과이다.
This interface provides random read-write access to the result set returned by a database query. (안드로이드 홈피)
