package com.sa22.entities;

public class Book {


        private String inventoryId;
        private String bookName;
        private String authorName;
        private String issueDate;

        public Book() {
        }


        public Book(String inventoryId, String bookName, String authorName, String issueDate) {
            this.inventoryId = inventoryId;
            this.bookName = bookName;
            this.authorName = authorName;
            this.issueDate = issueDate;

        }

        public void setBook(String inventoryId, String bookName, String authorName, String issueDate) {
            this.inventoryId = inventoryId;
            this.bookName = bookName;
            this.authorName = authorName;
            this.issueDate = issueDate;

        }


        public String getInventoryId(String s) {
            return inventoryId;
        }

        public void setInventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        @Override
        public String toString() {
            return "Inventory ID = "
                    + inventoryId
                    + ", Book Name = '"
                    + bookName
                    + '\''
                    + ", Author Name = '"
                    + authorName
                    + '\''
                    + ", Issue Date = "
                    + issueDate;
        }
    }
