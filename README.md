# 1. ���¼ҽ� ���

* springfox : API �׽�Ʈ �� ��� ������ ���� swagger-ui�� ����Ͽ����ϴ�.
* swagger-ui URL : http://localhost:8080/swagger-ui.html
    
------


# 2. API ����

### 1) ��α� �����ȸ API

* ����
	* Kakao API ���� �� Naver API�� ��ȸ�ϰ� Ű���带 �����մϴ�.
	* īī�� �� ���̹� ��α� ��ȸ API�� ���� �߰��� ��������ϴ�.
	* ��α� ��ȸ API�� �� 3���������� ������ ��� �����ϴ�.

* Request
	* query : �˻���	
	* sort : ���� Ű	
	* page : Page No	
	* size : Per Page

* Response
    * name : ��ΰ� ��
    * title : ��α� �� (īī�� ���)
    * contents : ��α� ����
    * link : ��α� ��ũ
    * bloggerLink : ��α� �� (���̹� ���)
	* thumbnail : ��α� ����� �̹��� ��ũ
    * date : �����


```sh
curl -X GET "http://localhost:8080/search/blogs?query=test&sort=accuracy&page=1&size=10" -H "accept: application/json"

curl -X GET "http://localhost:8080/search/kakao/blogs?query=test&sort=accuracy&page=1&size=10" -H "accept: application/json"

curl -X GET "http://localhost:8080/search/naver/blogs?query=fszf&sort=sim&page=1&size=10" -H "accept: application/json"
```
	
------

### 2) �α� �˻��� �����ȸ API 

* ����
	* ��α� �˻� Ű���带 ���� ���� ������ 10���� �����Ͽ� �����մϴ�.

* Request
	* ����

* Response
    * keyword : �˻���
    * search_count : ���� �˻� ��
    * contents : ��α� ����
    * link : ��α� ��ũ
    * bloggerLink : ��α� �� (���̹� ���)
	* thumbnail : ��α� ����� �̹��� ��ũ
    * date : �����


```sh
curl -X GET "http://localhost:8080/search/history" -H "accept: application/json"
```

------