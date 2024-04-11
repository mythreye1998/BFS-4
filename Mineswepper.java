class Solution {

    int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}};

    public char[][] updateBoard(char[][] board, int[] click) {
        if(board == null || click == null){
            return board;
        }

        int rows = board.length;
        int cols = board[0].length;

        int r = click[0];
        int c = click[1];

        if(board[r][c] != 'M' && board[r][c] != 'E'){
            return board;
        }

        if(board[r][c] == 'M'){
            board[r][c] = 'X';
            return board;
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        queue.offer(new int[]{r,c});
        
        int mines = getMines(board, r, c);
        if(mines > 0){
            board[r][c] = (char) (mines + '0');

            return board;
        }
        board[r][c] = 'B';
        visited[r][c] = true;

        while(!queue.isEmpty()){
            int[] currCell = queue.poll();
            int currR = currCell[0];
            int currC = currCell[1];

            for(int d = 0; d < directions.length; d++){
                int[] dir = directions[d];

                int newR = currR + dir[0];
                int newC = currC + dir[1];

                if(newR >= 0 && newR < rows && newC >= 0 && newC < cols && !visited[newR][newC]){
                    if(board[newR][newC] == 'M'){
                        visited[newR][newC] = true;
                        continue;
                    } else if(board[newR][newC] == 'E'){
                        mines = getMines(board, newR, newC);
                        if(mines > 0){
                            board[newR][newC] = (char) (mines + '0');
                            visited[newR][newC] = true;
                            continue;
                        }

                        board[newR][newC] = 'B';
                        visited[newR][newC] = true;
                        queue.offer(new int[]{newR, newC});
                    }
                }
            }
        }

        return board;
    }


    private int getMines(char[][] board, int row, int col){
        int mines = 0;

        for(int d = 0; d < directions.length; d++){
            int[] dir = directions[d];

            int newR = row + dir[0];
            int newC = col + dir[1];

            if(newR >= 0 && newR < board.length && newC >= 0 && newC < board[0].length && board[newR][newC] == 'M'){
                mines++;
            }
        }

        return mines;
    }

}
