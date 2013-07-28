package org.castafiore.beans;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Sudoku {

	
	
	private int gridSize = 9;
	
	
	private Random random = new Random();

	private Integer[][] grid ;
	
	
	
	
	
	public Sudoku(int gridSize) {
		super();
		this.gridSize = gridSize;
		grid = new Integer[gridSize][gridSize];
	}

	private Integer getRandomNumberExcluding(Set<Integer> excludes){
			int r = random.nextInt(10);
			
			
			if(excludes.size()< 9){
				while(r ==0 ||excludes.contains(r)){
					r = random.nextInt(10);
				}
				return r;
			}else{
				return -2;
			}
		
		
		//return excludes.size() + 1;
			
			
	}
	
	private Set<Integer> getRelatedNumbers(int x, int y){
		Set<Coordinate> related = getRelatedCoordinates(x, y);
		Set<Integer> result = new HashSet<Integer>();
		for(Coordinate c : related){
			if(grid[c.x][c.y] != null){
				result.add(grid[c.x][c.y]);
			}
		}
		//System.out.println(result.size());
		return result;
	}
	
	
	private Set<Coordinate> getRelatedCoordinates(int x , int y){
		
		Set<Coordinate> result = new HashSet<Coordinate>();
		for(int i = 0; i < gridSize; i++){
			
			result.add(new Coordinate(x, i));
			result.add(new Coordinate(i, y));
		}
		int bigX = x%3;
		int bigY = y%3;
		System.out.println("[" +bigX + "," + bigY  +"]:"+ result);
		for(int i = 0; i < 3; i ++){
			for(int j = 0; j < 3; j++){
				int nx = i+(bigX*3);
				int ny = j+(bigY*3);
				//boolean present = false;
//				for(Coordinate pres : result){
//					if(pres.x == nx && pres.y == ny){
//						present = true;
//					}
//				}
				if(true){
					Coordinate c = new Coordinate(nx, ny);
					System.out.println(c);
					result.add(c);
					//present = false;
				}
				
			}
		}
		System.out.println("Size:"+ result.size());
		return result;
		
	}
	
	

	
	private Integer getNumber(int x, int y){
		Set<Integer> relatedNumbers = getRelatedNumbers(x, y);
		if(relatedNumbers.size() == 10){
			System.out.println(relatedNumbers.size());
		}
		Integer number = getRandomNumberExcluding(relatedNumbers);
		return number;
	}
	
	
	public void build(){
		for(int i = 0; i < gridSize; i ++){
			for(int j = 0; j < gridSize; j ++){
				Integer number = getNumber(i, j);
				grid[i][j] = number;
			}
		}
	}
	
	
	public String toString(){
		for(int i = 0; i < gridSize; i ++){
			for(int j = 0; j < gridSize; j ++){
				Integer number = grid[i][j];
				System.out.print(number + "\t");
				
			}
			System.out.println("\n");
		}
		
		return "";
	}
	
	
	
	private class Coordinate{
		private Integer x;
		private Integer y;
		public Integer getX() {
			return x;
		}
		public void setX(Integer x) {
			this.x = x;
		}
		public Integer getY() {
			return y;
		}
		public void setY(Integer y) {
			this.y = y;
		}
		public Coordinate(Integer x, Integer y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object obj) {
			return (((Coordinate)obj).x.equals(x)) &&(((Coordinate)obj).y.equals(y));
		}
		
		
		@Override
		public int hashCode() {
			return x.hashCode() + y.hashCode();
		}
		@Override
		public String toString() {
			return ("[" +x + "," + y  +"]");
		}
		
		
	}
	
	public static void main(String[] args) {
		Sudoku s = new Sudoku(9);
		s.build();
		s.toString();

	}

}
