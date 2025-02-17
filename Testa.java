public class Testa {
    public static void main(String[] args) {
        int[][] statearr =  {{1,2,3},{4,5,0},{6,7,8}};
        var state = new NPuzzleState(3,statearr);
        var s = new BreadthFirstSearcher<NPuzzleAction, NPuzzleState>(
            state,
            NPuzzleState.makeGoal(3)
        );
        var sol = s.findFirst();
        System.out.println(s.getExpansions());
        System.out.println(sol.STATE);
        while(sol.PRIOR != null) {
            System.out.println(sol.ACTION);
            sol = sol.PRIOR;
            System.out.println(sol.STATE);
        }
    }
}