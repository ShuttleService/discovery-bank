package tech.ioco.discovery.bank;

public class Book {
    private final int pageNumber;
    private final int itemsPerPage;

    public Book(int pageNumber, int itemsPerPage) {
        this.pageNumber = pageNumber;
        this.itemsPerPage = itemsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    @Override
    public String toString() {
        return String.format("{pageNumber:%d, itemsPerPage:%d}", pageNumber, itemsPerPage);
    }
}
