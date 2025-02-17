public class SearchPath<SearchStateT, ActionT> {

    public final SearchPath<SearchStateT,ActionT> PRIOR;
    public final ActionT ACTION;
    public final SearchStateT STATE;

    public SearchPath(SearchPath<SearchStateT,ActionT> prior, ActionT action, SearchStateT state) {
        this.PRIOR = prior;
        this.ACTION = action;
        this.STATE = state;
    }

    public SearchPath(SearchStateT startNode) {
        this.PRIOR = null;
        this.ACTION = null;
        this.STATE = startNode;
    }
}