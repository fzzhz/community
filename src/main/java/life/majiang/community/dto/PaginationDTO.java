package life.majiang.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer toalPage;

    public List<QuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
        this.questionDTOs = questionDTOs;
    }

    public Boolean getShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(Boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public Boolean getShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(Boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public Boolean getShowNext() {
        return showNext;
    }

    public void setShowNext(Boolean showNext) {
        this.showNext = showNext;
    }

    public Boolean getShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(Boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public Integer getToalPage() {
        return toalPage;
    }

    public void setToalPage(Integer toalPage) {
        this.toalPage = toalPage;
    }

    public void setPagination(Integer totalPage, Integer page, Integer size) {
        this.page = page;
        this.toalPage = totalPage;
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page - i > 0){
                pages.add(0,page - i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }

        if (!pages.contains(1)){
            showFirstPage = true;
        }else {
            showFirstPage = false;
        }

        if (!pages.contains(totalPage)){
            showEndPage = true;
        }else {
            showEndPage = false;
        }
    }
}