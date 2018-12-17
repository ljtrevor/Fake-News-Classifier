# Fake News Classifier

This is a data mining project where a multinomial Naïve Bayes classifier is built to differentiate between real news and fake news by using a news article’s text. 

## Details

A data set of 11,945 news articles was collected, 11,282 were fake and 663 were real. The data collection process included scraping real news articles from CBC’s RSS feeds and filtering an open-source fake news dataset from Kaggle. Only news articles written in English were used in this project. Preprocessing of this data set was done by reducing all terms to lowercase, stripping out extraneous punctuation, and stripping out common stop words from the articles. The classifier was then trained and tested 100 times. Taking a random subset of 200 real news articles and 200 fake news articles, the classifier was able to achieve average values of 97.59% accuracy, 73.47% precision and 70.19% recall.

## Dependencies
JDK 8+ (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Running
To run this program on your own news articles, download the zip into a local directory.

$ git clone https://github.com/ljtrevor/Fake-News-Classifier.git

The program is contained in Project.zip so unzip it and navigate into the Project folder.

Compile: javac -cp.;opencsv-3.9.jar;commons-lang2-2.5.jar naiveBayes.java

Run: java -cp .;opencsv-3.9.jar;commons-lang3.3.5.jar naiveBayes article.txt

The "article.txt" holds the article that is being classified. the output will be displayed in the command prompt.

## Credit
This is an unlicensed project created by Trevor Lee, Myan Panikkar, Kaylen Kallio, and Felicity Rhone.

This project is currently unsupported.

## References
[1] Hunt, E. 2016. “What is fake news? How to spot it and what you can do to stop it”. The Guardian. [Online]. Available: https://www.theguardian.com/media/2016/dec/18/what-is-fake-news-pizzagate.

[2] Carson, J. 2017. “What is fake news? Its origins and how it grew in 2016”. The Telegraph. [Online]. Available: http://www.telegraph.co.uk/technology/0/fake-news-origins-grew-2016/.

[3] Connolly, K., Chrisafis, A., McPherson, P., Kirchgaessner, S., Haas, B., Phillips, D., Hunt, E., Safi, M. 2016. “Fake news: an insidious trend that's fast becoming a global problem”. The Guardian. [Online]. Available:
10
https://www.theguardian.com/media/2016/dec/02/fake-news-facebook-us-election-around-the-world.

[4] McClure, L. 2017. “How to tell fake news from real news”. TED Ed. [Online]. Available: http://blog.ed.ted.com/2017/01/12/how-to-tell-fake-news-from-real-news/. 

[5] Sieradski, D. BS Detector. [Online]. Available: https://github.com/selfagency/bs-detector.

[6] Risdal, M. “Getting Real About Fake News”. Kaggle. [Online]. Available: https://www.kaggle.com/mrisdal/fake-news. 

[7] Doyle, D. Ranks NL. [Online]. Available: http://www.ranks.nl/stopwords.

[8] Kallio, K., Lee, T., Panikkar, M., Rhone, F. 2017. SENG-474. [Online]. Available: https://github.com/Pandakar/SENG-474.

[9] Krishnalal, G., Rengarajan, S. B., Srinivasagan, K. G. 2010. “A new text mining approach based on HMM-SVM for web news classification.” International Journal of Computer Applications. doi:10.5120/395-589

[10] Stanford History Education Group. 2016. “Evaluating information: the cornerstone of civic online reasoning.” Stanford History Education Group. [Online]. Available: https://sheg.stanford.edu/upload/V3LessonPlans/Executive%20Summary%2011.21.16.pdf.





