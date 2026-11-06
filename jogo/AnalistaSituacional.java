package jogo;

public class AnalistaSituacional
{
    private Tabuleiro tabDoJogo;

    public AnalistaSituacional (Tabuleiro tab) throws Exception
    {
        if (tab==null) throw new Exception ("Tabuleiro ausente");

        // A análise deve acompanhar as jogadas na mesma instância.
        this.tabDoJogo = tab;
    }

    public Marca getMarcaDoVencedor ()
    {
        try
        {
            for (int lin=0; lin<3; lin++)
            {
                Marca marca = this.tabDoJogo.getMarcaNaPosicao (lin, 0);
                if (marca!=null && marca.equals (this.tabDoJogo.getMarcaNaPosicao (lin, 1)) &&
                                   marca.equals (this.tabDoJogo.getMarcaNaPosicao (lin, 2)))
                    return marca;
            }

            for (int col=0; col<3; col++)
            {
                Marca marca = this.tabDoJogo.getMarcaNaPosicao (0, col);
                if (marca!=null && marca.equals (this.tabDoJogo.getMarcaNaPosicao (1, col)) &&
                                   marca.equals (this.tabDoJogo.getMarcaNaPosicao (2, col)))
                    return marca;
            }

            Marca marca = this.tabDoJogo.getMarcaNaPosicao (1, 1);
            if (marca!=null)
            {
                if (marca.equals (this.tabDoJogo.getMarcaNaPosicao (0, 0)) &&
                    marca.equals (this.tabDoJogo.getMarcaNaPosicao (2, 2)))
                    return marca;
                if (marca.equals (this.tabDoJogo.getMarcaNaPosicao (0, 2)) &&
                    marca.equals (this.tabDoJogo.getMarcaNaPosicao (2, 0)))
                    return marca;
            }

            return null;
        }
        catch (Exception erro)
        {
            throw new IllegalStateException ("Não foi possível analisar o vencedor", erro);
        }
    }

    private boolean tabuleiroCheio ()
    {
        try
        {
            for (int lin=0; lin<3; lin++)
                for (int col=0; col<3; col++)
                    if (!this.tabDoJogo.haMarcaNaPosicao (lin, col))
                        return false;

            return true;
        }
        catch (Exception erro)
        {
            throw new IllegalStateException ("Não foi possível verificar o tabuleiro", erro);
        }
    }

    public boolean fimDeJogo ()
    {
        return this.getMarcaDoVencedor ()!=null || this.tabuleiroCheio ();
    }

    @Override
    public String toString ()
    {
        Marca vencedor = this.getMarcaDoVencedor ();
        if (vencedor!=null) return "Vencedor: "+vencedor;
        if (this.tabuleiroCheio ()) return "Empate";
        return "Partida em andamento";
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (obj==null) return false;
        if (this.getClass()!=obj.getClass()) return false;

        AnalistaSituacional analista = (AnalistaSituacional)obj;
        return this.tabDoJogo.equals (analista.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 2*ret + this.tabDoJogo.hashCode ();

        if (ret<0) ret = -ret;
        if (ret<0) ret = 0;
        return ret;
    }
}
