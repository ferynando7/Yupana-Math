package com.example.algebra;

public class Matrix {

    private int m;
    private int n;
    private double[][] elem;

    ////////////CONSTRUCTORES///////////////////////////////////////////////
    public Matrix(Matrix another) {
        this.m=another.getM();
        this.n=another.getN(); 
        
        double[][] tempElems = new double[m][n];  
        this.elem = tempElems;
             
        for (int i = 0; i<m; i++){
            for (int j = 0; j<n; j++){	
            	this.setPos(i, j, another.getPos(i, j));
            }
        }
        
        
        //this.elem = another.getElements(); // you can access
    }

    public Matrix(int m,int n) {
        this.m = m;
        this.n = n;
        this.elem = new double[m][n];
        //this.n=n;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                elem[i][j] = 0.0;
            }
        }
    }

    public Matrix(String forma,int m, int n){
        this.m=m;
        this.n=n;
        this.elem= new double[m][n];

        if (forma=="unos") {
            for (int cont3 = 0; cont3 < m; cont3++) {
                for (int cont2 = 0; cont2 < n; cont2++) {
                    this.elem[cont3][cont2] = 1;
                }
            }
        }else if(forma=="eye"){
            for (int j=0; j<n;j++){
                for (int i=0;i<m;i++){
                    if (i==j){
                        this.elem[i][j]=1;
                    }else{
                        this.elem[i][j]=0;
                    }
                }
            }
        }
    }

    public Matrix(double[][] elems){
        this.m=elems.length;
        this.n=elems[0].length;
        this.elem=elems;
    }

    public Matrix(double[] elems) {
        this.m = elems.length;
        this.n = 1;

        double[][] casiElems = new double[this.m][this.n];

        for (int i = 0; i < m; i++) {
            casiElems[i][0] = elems[i];
        }

        this.elem = casiElems;
    }



////////////////FIN CONSTRUCTORES//////////////////////////////////////////


    
////////////////SETTERS Y GETTERS//////////////////////////////////////////

    /**Asigna valores a la matriz**/
    public void setElements(double[][] elems) {
        this.elem = elems;
    }    

    public void setM(int m) {
        this.m = m;
    }    

    public void setN(int n) {
        this.n = n;
    }    
    
    
    public int getM(){
    	return this.m;
    }
    
    public int getN(){
    	return this.n;
    }
    

    public double[][] getElements(){
        return this.elem;
    }
    
////////////////FIN SETTERS Y GETTERS//////////////////////////////////////////
    

    /**Devuelve el valor de la posicion de una matriz*/
    public double getPos(int fila, int columna){return this.elem[fila][columna];
    }

    /**Devuelve la matriz editada en una sola posicion*/
    public void setPos(int fila, int columna, double valor){
        this.elem[fila][columna] = valor;
    }
    
    
    /**Devuelve las dimensiones de la matriz*/
    public int[] dim(){
        int[] dimensiones={this.m,this.n};
        return dimensiones;
    }

    //** Intercambiar filas ANTHONY RAMOS  */
    public void intercambiarFilas(int fila1,int fila2) {
    	
    		
    	Matrix auxF1= this.subMatrix(fila1, fila1, 0, n-1);// Copiamos fila 1
    	this.asignar(fila1, fila1, 0, n-1, this.subMatrix(fila2, fila2, 0, n-1));
    	this.asignar(fila2, fila2, 0, n-1, auxF1);
    	
    }
    
    /** Encontrar 0 */

    public boolean areAllZeros(Matrix another) {
    	
    	boolean bol = true;
    	for(int i = 0 ; i<n && bol ; i++){
    		if(another.getPos(0, i)!=0){
    			bol = false;
    		}
    	}
    	return bol;
    	
    }
    
    /**  Funcion que extrae el indice del elmento mayor de un vector ANTHONY */
    	
    public int indiceMax(Matrix another){
    	double max = another.getPos(1,0);
    	int length = another.dim()[0];
    	int index = 0;
    	for (int i = 1 ; i<length ; i++){
    		double elemento = another.getPos(i,0);
    		if (max < elemento){
    			index = i;
    			max = elemento;
    		}
    	}
    	return index;
    }
    
    
    /** Algoritmo de Eliminacion ANTHONY */
    
    
    public Matrix Eliminacion(){
    	int pivot = 0;
    	for(int j = 0 ; j<this.m-1 ; j++){
    		pivot = indiceMax(this.subMatrix(j, m-1, j, j));
    		if(pivot != 0) this.intercambiarFilas(pivot, j); 		
    		for(int i = j; i<m-1; i++){
    			double a1 = this.getPos(j,j) ;
    			if (a1 != 0.0){
    				double a2 = this.getPos(i+1, j);
    				double coef = a1/a2;    				

    				Matrix fila1 = (this.subMatrix(i+1, i+1, 0, n-1)).prod(coef);
    				
    				Matrix fila2 = this.subMatrix(j, j, 0, n-1);

    				this.asignar(i+1, i+1, 0, n-1, fila1.resta(fila2));
       			}
    		}    		   
    	}
    	return this;
    }
    
    /** Rango Anthony */
    public int rango(){
    	//FZ Necesito hacer la copia de A para hacer la eliminacion 
    	Matrix A = new Matrix(this);
    	
    	A = A.Eliminacion();
    	int ran = m;
    	for(int i = m-1; i >= 0 ; i-- ){
    		if( areAllZeros(A.subMatrix(i, i, 0, n-1))){ //FZ le puse A porque era una variable que estaba sin usarse y ademas necesitaba conservar la matriz original
    			ran = ran - 1 ; 
    		}    				
    	}
    	return ran;
    }
    
    /** Ampliar una matriz Anthony */
    public Matrix ampliada(Matrix b){
    	int columnasC = this.n +1;
    	Matrix Completa = new Matrix("unos",m,n+1);
    	Completa.asignar(0, m-1, 0, n-1, this);
    	Completa.asignar(0, m-1, columnasC-1, columnasC-1, b.subMatrix(0, m-1, 0,0));
    	return Completa;
    }
    
    /** Teorema de Rouche */
    public int tieneSol(Matrix b){
    	int result;
    	
    	int rankA = this.rango();
    	int rankAB = this.ampliada(b).rango();
    	
    	if(rankA != rankAB){
    		result = 0;
    	}else if(rankA != n){
    		result = 10;
    	}else{
    		result = 11;
    	}
    	
    	return result;
    }
    
    
    /**Devuelve la representacion textual de la matriz*/
    public String imprimir() {

        StringBuffer texto = new StringBuffer();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                texto.append(this.elem[i][j]);
                texto.append(";");
            }
            texto.append("\n");
        }
        return texto.toString();
    }

    /*Devuelve la matriz editada en las posiciones indicadas*/
    public Matrix asignar(int inicFila, int finFila, int inicCol, int finCol, Matrix another){

        int contF=0;
        for(int i=inicFila;i<=finFila;i++){
            int contC= 0;
            for (int j=inicCol;j<=finCol;j++){
                elem[i][j]=another.elem[contF][contC];
                contC+=1;
            }
            contF+=1;
        }
        return this;
    }

    /**Devuelve la traspuesta de la matriz*/
    public Matrix trasp(){

        Matrix Trasp = new Matrix(n,m);

        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                Trasp.elem[j][i]=this.elem[i][j];
            }
        }
        return Trasp;
    }

    /**Devuelve el producto de la matriz por un escalar*/
    public Matrix prod(double factor) {

    	double[][] temp = this.getElements();
    	
    	Matrix Producto = new Matrix(elem);
    	
        //Matrix Producto=new Matrix(this);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
//                this.elem[i][j] = (this.elem[i][j]) * factor;
                this.elem[i][j] = (this.elem[i][j]) * factor;
                
            }
        }
        return Producto;
    }

    /**Devuelve el producto de la matriz por otra*/
    public Matrix prod(Matrix another) {

        Matrix Producto= new Matrix(this.m,another.n);
        if (this.n != another.m){
            Producto.m=1;
            Producto.n=1;
            Producto.elem[0][0] =0;
        }else{
            for (int cont3 = 0; cont3<this.m;cont3++){
                for (int cont2 = 0; cont2<another.n;cont2++){
                    double acum=0;
                    for (int cont = 0; cont<this.n;cont++){
                        acum += this.elem[cont3][cont]*another.elem[cont][cont2];
                    }
                    Producto.elem[cont3][cont2]=acum;
                }
            }

        }
        return Producto;
    }

    /**Devuelvela norma 2 de una matriz n*uno  */
    public double norma2V(){
        double Norma2V;
        double acum = 0;
        for (int i=0;i<m;i++){
            acum += (this.elem[i][0])*(this.elem[i][0]);
        }
        Norma2V = Math.sqrt(acum);
        return Norma2V;
    }

    /**Devuelve la suma de 2 matrices */
    public Matrix suma(Matrix another){
        Matrix Suma= new Matrix(this.m,this.n);
        if (this.m != another.m || this.n != another.n){
            Suma.m=1;
            Suma.n=1;
            Suma.elem[0][0] = 0;
        }else{
            for (int cont3 = 0; cont3<this.m;cont3++){
                for (int cont2 = 0; cont2<this.n;cont2++){
                    Suma.elem[cont3][cont2]=this.elem[cont3][cont2]+another.elem[cont3][cont2];
                }
            }
        }
        return Suma;
    }

    /**Devuelve la resta 2 matrices*/
    public Matrix resta(Matrix another){
        Matrix Diferencia= new Matrix(this.m,this.n);
        if (this.m != another.m || this.n != another.n){
            Diferencia.m=1;
            Diferencia.n=1;
            Diferencia.elem[0][0] = 0;

        }else{
            for (int cont3 = 0; cont3<this.m;cont3++){
                for (int cont2 = 0; cont2<this.n;cont2++){
                    Diferencia.elem[cont3][cont2]=this.elem[cont3][cont2]-another.elem[cont3][cont2];
                }
            }

        }
        return Diferencia;
    }

    /**Devuelve el valor maximo de la matriz*/
    public double max(){
        double maximo = this.elem[0][0];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if (this.elem[i][j]>maximo){
                    maximo=this.elem[i][j];
                }
            }
        }
        return maximo;
    }

    /*Devuelve el valor minimo de la matriz*/
    public double min(){
        double minimo = this.elem[0][0];
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if (this.elem[i][j]<minimo){
                    minimo=this.elem[i][j];
                }
            }
        }
        return minimo;
    }

    /**Devuelve una matriz con los valores absolutos de sus elementos*/
    public Matrix absMatrix(){

        double[][] newElem=this.elem;

        Matrix Abs=new Matrix(this.m,this.n);
        for (int i=0;i<this.m;i++){
            for (int j=0;j<this.n;j++){
                if(this.elem[i][j]<0){
                    Abs.elem[i][j]=-this.elem[i][j];
                }else if(this.elem[i][j]>0){
                    Abs.elem[i][j]=this.elem[i][j];
                }
            }
        }
        return Abs;
    }

    /**Devuelve la matriz "sign"
     * la matriz sign es una matriz que pone un -1 si el elemento es negativo y
     * pone un 1 si el elemento es positivo
     */
    public Matrix sign(){
        Matrix Sign=new Matrix(this.m,this.n);
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                if(this.elem[i][j]<0){
                    Sign.elem[i][j]=-1;
                }else if(this.elem[i][j]>0){
                    Sign.elem[i][j]=1;
                }
            }
        }

        return Sign;
    }

    /**Devuelve el valor entero de una cadena*/
    private int[] StringToInt(String cadena){
        String[] cadenaSep=(cadena.split(":"));
        int[] salida = new int[cadenaSep.length];
        for (int i = 0; i < cadenaSep.length; i++) {
            salida[i] = Integer.parseInt(cadenaSep[i]);
        }
        return salida;
    }

    /**Devuelve la submatriz de una matriz*/
    public Matrix subMatrix(int inicFila, int finFila, int inicCol, int finCol){

        int m= Math.abs(finFila-inicFila)+1;
        int n= Math.abs(finCol-inicCol)+1;

        Matrix sub = new Matrix(m,n);

        int contI=0;

        for (int i=inicFila;i<=finFila;i++){
            int contJ= 0;
            for (int j=inicCol;j<=finCol;j++){
                sub.elem[contI][contJ]=this.elem[i][j];
                contJ+=1;
            }
            contI+=1;
        }
        return sub;
    }

    /*Devuelve la matriz triangular superior*/
    public Matrix triu(){
        Matrix Triu=new Matrix(m,n);
        for(int i= 0;i<m;i++){
            for(int j=0;j<n;j++){
                if(j>=i){
                    Triu.elem[i][j]=this.elem[i][j];
                }else{
                    Triu.elem[i][j]=0;
                }
            }
        }
        return Triu;
    }


    /**Devuelve el determinante de una matriz*/
    public double det(){
        double determ=0;
        double[][] a = this.getElements();
        int size[] = this.dim();
        int n = size[0];
        int m = size[1];

        if (n==1){
            determ = a[0][0];
        }else if(n==2){
            determ = a[0][0]*a[1][1]-a[0][1]*a[1][0];
        }else if(n==3){
            determ = a[0][0]*a[1][1]*a[2][2] + a[0][1]*a[1][2]*a[2][0] + a[1][0]*a[2][1]*a[0][2]
                    - a[2][0]*a[1][1]*a[0][2] - a[0][0]*a[2][1]*a[1][2] - a[1][0]*a[0][1]*a[2][2];
        }else {
            for(int j=0;j<m;j++){
                determ+= a[0][j]*cofactor(0,j);
            }
        }

        return determ;
    }

    /**Devuelve el menor de una matriz*/
    public Matrix menor(int i, int j){
        int[] size = this.dim();
        int n = size[0], m = size[1];
        double[][] elems = this.getElements();

        Matrix mA = new Matrix(n-1,m-1);

        int o=0,p=0;

        for (int k=0;k<n;k++){
            p=0;

            if(k==i) {
                continue;
            }
            for (int l=0;l<m;l++){
                if(l==j) {
                    continue;
                }
                mA.setPos(o,p,elems[k][l]);
                p++;
            }
            o++;
        }
        return mA;
    }

    public double cofactor (int i, int j){

        return this.menor(i,j).det()*Math.pow(-1,i+j);
    }

    public Matrix regresiva(Matrix B){
        int n = B.dim()[0];

        Matrix X = new Matrix(n,1);

        double[][] b = B.getElements();
        double[][] A = this.getElements();
        double[][] x = X.getElements();


        for (int i = n-1;i>=0;i--){
            x[i][0] = b[i][0];
            for (int j = i+1; j<n;j++){
                x[i][0] = x[i][0] - A[i][j]*x[j][0];
            }
            x[i][0] = x[i][0]/A[i][i];
        }

        X.setElements(x);
        return X;
    }



    /*****************************************/
    /**Desarrollo de las funciones previas al algoritmo QR*/

    /**Funcion housecero:
     * crea ceros en un vector usando una matriz Householder.
     produce un vector u que define una matriz Householder H, y
     una constante sigma
     */
    public Matrix[] housecero(Matrix x) {
        int[] sizeX = x.dim();
        int m = sizeX[0];

        double max = x.absMatrix().max();
        x = x.prod(1 / max);
        double s = x.sign().getPos(0,0);
        if (s == 0) {
            s = 1;
        }
        double sigma = s * x.norma2V();
        Matrix u = x.suma(new Matrix("eye",m,1).prod(sigma));
        sigma = -max * sigma;

        Matrix Sigma = new Matrix(new double[][]{{sigma}});

        Matrix[] houseCero = {u,Sigma};

        return houseCero;
    }

    /**Funcion housemult;
     * postmultiplica una matriz por una matriz Householder H
     * Calcula AH, donde H es una matriz HOuseholder generada
     * por un vector u. La matriz resultante contiene el producto AH
     */
    public Matrix housemult(Matrix A, Matrix u) {

        int[] sizeA = A.dim();
        int m = sizeA[0];
        int n = sizeA[1];
        double s;

        double beta = 2 / (u.trasp().prod(u)).getPos(0,0);

        for (int i = 0; i < m; i++) {

            Matrix uSub = u.subMatrix(0,n-1,0,0);
            Matrix ASub = A.subMatrix(i,i,0,n-1);

            s = ASub.prod(uSub).getPos(0,0);
            s = s * beta;

            Matrix nFila = ASub.resta(uSub.prod(s).trasp());
            A = A.asignar(i,i, 0,n-1, nFila);

        }
        return A;
    }


    /**Funcion houseQR:
     *Factorizacion QR de una matriz A usando matrices Householder
     *houseQR produce an ortogonal matriz Q y una matriz triangular
     *superior R del mismo tama�o que A con ceros debajo de la diagonal
     * A tal que  A = QR.
     * Esta funcion llama a los metodos housecero y housemult
     */
    public Matrix[] houseQR() {
        Matrix A = new Matrix(this);
        int[] sizeA = A.dim();
        int m = sizeA[0];
        int n = sizeA[1];

        int S = Math.min(n, m-1);
        Matrix Q = new Matrix("eye", m, m);
        double s;

        for (int k = 0;k < S; k++) {


            Matrix[] xSigma = housecero(A.subMatrix(k,m-1,k,k));
            Matrix x = xSigma[0];
            double sigma = xSigma[1].getPos(0,0);



            Q = Q.asignar(0,m-1,k,m-1,housemult(Q.subMatrix(0,m-1,k,m-1),x));
            A.setPos(k,k,sigma);
            int s1 = x.dim()[0];


            A = A.asignar(k+1,m-1,k,k,x.subMatrix(1,s1-1,0,0));
            double beta = 2/(x.trasp().prod(x).getPos(0,0));

            for (int j = k + 1; j < n; j++) {

                Matrix xSub = (x.subMatrix(0,m-k-1,0,0));
                s = (((xSub).trasp()).prod(A.subMatrix(k,m-1,j,j)).getPos(0,0));
                s = beta * s;

                Matrix asignarA = (A.subMatrix(k,m-1,j,j)).resta((x.subMatrix(0,m-1-k,0,0)).prod(s));
                A = A.asignar(k,m-1,j,j,asignarA);
            }

        }
        Matrix R = A.triu();
        Matrix[] QR = {Q, R};
        return QR;
    }
}