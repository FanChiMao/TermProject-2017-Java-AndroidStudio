# 即時課堂互動 Classroom Interaction App (畢業專題 2017)  
## 指導教授: 趙一芬  
## 組員: 許宸瑜, 謝子晏, 范植貿, 周易凱  

Poster: [png](https://drive.google.com/file/d/1pPpoq9PRbmME50cs4kl6S2eDQKlYFMkZ/view?usp=sharing)  

Report: [word](https://docs.google.com/document/d/1tA1TyLsBKkUloF144vLz7zFzpF9rVBwj/edit?usp=sharing&ouid=108348190349543369603&rtpof=true&sd=true)  

Slides: [pdf](https://docs.google.com/presentation/d/1YsvzB4KOTbtPYtGbjwFzsWaPwZb21d-g/edit?usp=sharing&ouid=108348190349543369603&rtpof=true&sd=true)  

***
> 專題動機 :　往往電機系給人的印象是半導體、IC設計，而近年來，因為資料科學、大數據、人工智慧的發展，讓原本的家電、家庭、學校、城市等，越來越多的東西冠上智慧的稱號，為了就是能夠在我們生活中收集各式各樣的資訊，這些資訊也可以讓我們了解，並掌控更多東西去運用。在人手一枚智慧型手機的現在，成了我們最便捷這廣大智慧物聯網的輸入端，如何設計一個好的APP以準確的彙整資訊，且給使用者一個好上手的前端，未來產品我們也可以更快的整合所有環節的工作，又或自行開發簡易的前端APP。因此成為我們做這份專題的目的。  

## UI  

  |      |   Example images     |  
  | :--: | :------------------: |  
  | 學生 |<img src="https://i.ibb.co/tc8FdwJ/s-1.jpg" width="200" style="zoom:100%;"/> <img src="https://i.ibb.co/JpZcgZq/s-2.jpg" width="200" style="zoom:100%;"/> <img src="https://i.ibb.co/N6QnT7t/s-3.jpg" width="200" style="zoom:100%;"/>|  
  | 老師 |<img src="https://i.ibb.co/x3P8BSz/t-1.jpg" width="200" style="zoom:100%;"/> <img src="https://i.ibb.co/dK6fG1t/t-2.jpg" width="200" style="zoom:100%;"/> <img src="https://i.ibb.co/7N7SwGZ/t-3.jpg" width="200" style="zoom:100%;"/>|  
  
<details>  
<summary>More Figures: </summary>  
  
- 學生:  
  <img src="https://i.ibb.co/FxNwbf8/s-4.jpg" alt="s-4" width="200" style="zoom:100%;"/> 
  <img src="https://i.ibb.co/19Nc772/s-5.jpg" alt="s-5" width="200" style="zoom:100%;"/> 
  <img src="https://i.ibb.co/GCX7PqK/s-6.jpg" alt="s-6" width="200" style="zoom:100%;"/>  
  
- 老師:  
  <img src="https://i.ibb.co/ygvZ6WC/t-4.jpg" alt="t-4" width="200" style="zoom:100%;"/> 
  <img src="https://i.ibb.co/qykFK76/t-5.jpg" alt="t-5" width="200" style="zoom:100%;"/> 
  <img src="https://i.ibb.co/KGjy9zg/t-6.jpg" alt="t-6" width="200" style="zoom:100%;"/>  
  
</details>  

## Socket Connection  
<img src="https://i.ibb.co/Bsb9sWw/1.jpg" alt="1" width="500" >  

## Database (SQLite)  

- Student database table **學生資料庫**:  
  | 科目 |  章節   | 題目內容 | 題目數目 | 正確數目  | 
  | :---------: | :--------------: | :----------------------: | :----------------------: | :----------------------: |  
  | "Chinese"   | "Chapter 1"      | "國 怎麼寫?"              | 1                        | 1                        |  
  | "Chinese"   | "Chapter 1"      | "字 怎麼寫?"              | 1                        | 0                        |  
  | "Chinese"   | "Chapter 2"      | "國 怎麼拼?"              | 1                        | 1                        |  
  | "Math"      | "Chapter 1"      | "1 + 1 = ?"              | 1                        | 1                        |  
  | "English"   | "Chapter 1"      | "國 的英文?"              | 1                        | 0                        |  
  | ... | ... | ... | ... | ... |  
  
- Teacher database table **老師資料庫**:  
  - 題目資料庫  

  |科目|章節|題目內容|選項(A)內容|選項(B)內容|選項(C)內容|選項(D)內容|正確答案|時間設定|本題配分|出題時間|考試人數|  
  |:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|  
  | "數學"|"加法"| "1+1=?"|"1"|"2"|"3"|"4"|"2"|5|10|"2021.1.1"|1|  
  | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... |  
  
  - 學生成績資料庫  

  |班級|學生姓名|學生ID|本題配分|學生答對或錯|  
  |:--:|:--:|:--:|:--:|:--:|
  |電機甲組B班|范植貿|1040650|10|1|  
  | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... |  
  
***
> 收穫 : 這個專題不只帶給我們一門技術上的應用，最大的收穫是自主學習，學會如何自己從入門到精進；最大的課題是如何發現問題如何尋找答案。在科技迅速發展的未來，最重要的是找出問題的答案的能力。有問題就查資料，越多資料參考就越有機會解決問題；資料太少，自己想辦法，從問題中切入分析，思考是甚麼造成問題，從而往上游尋求答案，Debug是其中重要的一環；自己無法解決，就尋求同伴幫助，人會有盲點，有時候換個人看，提點一番會有很大的功效。  

***
> 分工 :  
> 許宸瑜: 網路socket部分  
> 謝子晏: 找尋資料、整彙報告  
> 范植貿: DataBase部分  
> 周易凱: 架構統整、UI部分、程式重構  




  
