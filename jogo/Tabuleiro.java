package jogo;

public class Tabuleiro implements Cloneable
{
    // A princípio, todas as posições são null.
    private Marca matriz [][];

    public Tabuleiro ()
    {
        this.matriz = new Marca [3][3];
    }

    public boolean haMarcaNaPosicao (int lin, int col) throws Exception
    {
        return this.getMarcaNaPosicao (lin, col)!=null;
    }

    public Marca getMarcaNaPosicao (int lin, int col) throws Exception
    {
        if (lin<0 || lin>2) throw new Exception ("Linha inválida");
        if (col<0 || col>2) throw new Exception ("Coluna inválida");

        return this.matriz[lin][col];
    }

    public void setMarcaNaPosicao (Marca mrc, int lin, int col) throws Exception
    {
        if (this.haMarcaNaPosicao (lin, col))
            throw new Exception ("Posição ocupada");
        if (mrc==null) throw new Exception ("Marca ausente");

        this.matriz[lin][col] = mrc;
    }

    @Override
    public String toString ()
    {
        String ret = "    0   1   2\n";

        for (int lin=0; lin<3; lin++)
        {
            ret += lin+"   ";
            for (int col=0; col<3; col++)
            {
                ret += this.matriz[lin][col]==null ? " " : this.matriz[lin][col].toString();
                if (col<2) ret += " | ";
            }
            ret += "\n";
            if (lin<2) ret += "   ---+---+---\n";
        }

        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj==this) return true;
        if (obj==null) return false;
        if (this.getClass()!=obj.getClass()) return false;

        Tabuleiro tab = (Tabuleiro)obj;
        for (int lin=0; lin<3; lin++)
            for (int col=0; col<3; col++)
                if (this.matriz[lin][col]==null)
                {
                    if (tab.matriz[lin][col]!=null) return false;
                }
                else if (!this.matriz[lin][col].equals (tab.matriz[lin][col]))
                    return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        for (int lin=0; lin<3; lin++)
            for (int col=0; col<3; col++)
                ret = 2*ret + (this.matriz[lin][col]==null ? 0 : this.matriz[lin][col].hashCode());

        if (ret<0) ret = -ret;
        if (ret<0) ret = 0;
        return ret;
    }

    // Construtor de cópia: a matriz pertence à nova instância; Marca é imutável.
    public Tabuleiro (Tabuleiro modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.matriz = new Marca [3][3];
        for (int lin=0; lin<3; lin++)
            for (int col=0; col<3; col++)
                this.matriz[lin][col] = modelo.matriz[lin][col];
    }

    @Override
    public Object clone ()
    {
        try
        {
            return new Tabuleiro (this);
        }
        catch (Exception erro)
        {
            // clone não permite propagar Exception checked nem deve ocultar falhas.
            throw new IllegalStateException ("Não foi possível copiar o tabuleiro", erro);
        }
    }
}
