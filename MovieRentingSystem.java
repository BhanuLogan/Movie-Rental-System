class Movie{
    int shop, movie, price;
    boolean rented;
    Movie(int shop, int movie, int price){
        this.shop = shop;
        this.movie = movie;
        this.price = price;
        this.rented = false;
    }
    public String toString(){
        return this.shop + " " + this.movie + " " + this.price + " " + this.rented;
    }
}
class MovieComparator implements Comparator<Movie>{
    public int compare(Movie a, Movie b){
        if(a.price == b.price){
            if(a.shop == b.shop)
                return a.movie - b.movie;
            return a.shop - b.shop;
        }
        return a.price - b.price;
    }
}
class MovieRentingSystem {
    HashMap<Integer, List<Movie>> shops;
    HashMap<Integer, List<Movie>> movies;
    List<Movie> rented;
    public MovieRentingSystem(int n, int[][] entries) {
       // System.out.println(n + " " + entries.length);
        shops = new HashMap<>();
        movies = new HashMap<>();
        rented = new ArrayList<>();
        for(int[] m : entries){
            int shop = m[0];
            int movie = m[1];
            int price = m[2];
            if(!shops.containsKey(shop))
                shops.put(shop, new ArrayList<>());
            if(!movies.containsKey(movie))
                movies.put(movie, new ArrayList<>());
            Movie mv = new Movie(shop, movie, price);
            shops.get(shop).add(mv);
            movies.get(movie).add(mv);
        }
    }
    public PriorityQueue<Movie> getMovies(List<Movie> list){
        PriorityQueue<Movie> heap = new PriorityQueue<>(new MovieComparator());
        for(Movie movie : list){
            if(!movie.rented)
                heap.add(movie);
        }
        return heap;
    }
    public List<Integer> search(int movie) {
        List<Integer> ans = new ArrayList<>();
        if(movies.containsKey(movie)){
            List<Movie> list = movies.get(movie);
            PriorityQueue<Movie> heap = getMovies(list);
            //System.out.println(heap);
            int k = 5;
            while(!heap.isEmpty() && k-- > 0){
                Movie mv = heap.poll();
                ans.add(mv.shop);
            }
        }
        return ans;
    }
    
    public void rent(int shop, int movie) {
        Movie m = null;
        for(Movie mv : shops.get(shop)){
            if(movie == mv.movie){
                mv.rented = true;
                m = mv;
                break;
            }
        }
        if(m != null)
            rented.add(m);
    }
    
    public void drop(int shop, int movie) {
        Movie m = null;
        for(Movie mv : shops.get(shop)){
            if(movie == mv.movie){
                mv.rented = false;
                m = mv;
                break;
            }
        }
        if(m != null)
            rented.remove(m);
    }
    
    public List<List<Integer>> report() {
        Collections.sort(rented, new MovieComparator());
        List<List<Integer>> ans = new ArrayList<>();
        //System.out.println(rented);
        for(int i = 0; i < Math.min(5, rented.size()); i++){
            Movie mv = rented.get(i);
            ans.add(Arrays.asList(mv.shop, mv.movie));
        }
        return ans;
    }
}
