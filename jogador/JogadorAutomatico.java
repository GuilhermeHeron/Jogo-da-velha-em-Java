package jogador;

import jogo.Marca;
import jogo.Tabuleiro;

public class JogadorAutomatico implements Jogador, Cloneable
{
    private Marca     marca     = null;
    private Tabuleiro tabDoJogo = null;

    public JogadorAutomatico (Tabuleiro tab) throws Exception
    {
        this (tab, new Marca ((int)(Math.random ()*2)==0 ? 'X' : 'O'));
    }

    public JogadorAutomatico (Tabuleiro tab, Marca mrc) throws Exception
    {
        if (tab==null) throw new Exception ("Tabuleiro ausente");
        if (mrc==null) throw new Exception ("Marca ausente");

        // A jogada precisa modificar o tabuleiro compartilhado da partida.
        this.tabDoJogo = tab;
        this.marca = mrc;
    }

    @Override
    public Marca getMarca ()
    {
        return this.marca;
    }

    // Só joga se a trinca tiver a quantidade desejada da marca e o restante vazio.
    private boolean tenteJogarNaTrinca (Marca mrc, int qtd,
                                       int lin0, int col0, int lin1, int col1,
                                       int lin2, int col2) throws Exception
    {
        Marca primeira = this.tabDoJogo.getMarcaNaPosicao (lin0, col0);
        Marca segunda  = this.tabDoJogo.getMarcaNaPosicao (lin1, col1);
        Marca terceira = this.tabDoJogo.getMarcaNaPosicao (lin2, col2);

        int iguais = 0;
        int vazias = 0;
        if (primeira==null) vazias++;
        else if (primeira.equals (mrc)) iguais++;
        if (segunda==null) vazias++;
        else if (segunda.equals (mrc)) iguais++;
        if (terceira==null) vazias++;
        else if (terceira.equals (mrc)) iguais++;

        if (iguais!=qtd || vazias!=3-qtd) return false;

        if (primeira==null)
            this.tabDoJogo.setMarcaNaPosicao (this.marca, lin0, col0);
        else if (segunda==null)
            this.tabDoJogo.setMarcaNaPosicao (this.marca, lin1, col1);
        else
            this.tabDoJogo.setMarcaNaPosicao (this.marca, lin2, col2);

        return true;
    }

    @Override
    public void facaSuaJogada ()
    {
        try
        {
            Marca adversaria = new Marca (this.marca.getSimbolo ()=='X' ? 'O' : 'X');

            // Tentando ganhar na linha 0: condições iniciadas no original.
            if (!this.tabDoJogo.haMarcaNaPosicao (0,0) &&
                 this.tabDoJogo.haMarcaNaPosicao (0,1) && this.tabDoJogo.getMarcaNaPosicao (0,1).equals (this.marca) &&
                 this.tabDoJogo.haMarcaNaPosicao (0,2) && this.tabDoJogo.getMarcaNaPosicao (0,2).equals (this.marca))
                this.tabDoJogo.setMarcaNaPosicao (this.marca, 0,0);
            else if (this.tabDoJogo.haMarcaNaPosicao (0,0) && this.tabDoJogo.getMarcaNaPosicao (0,0).equals (this.marca) &&
                    !this.tabDoJogo.haMarcaNaPosicao (0,1) &&
                     this.tabDoJogo.haMarcaNaPosicao (0,2) && this.tabDoJogo.getMarcaNaPosicao (0,2).equals (this.marca))
                this.tabDoJogo.setMarcaNaPosicao (this.marca, 0,1);
            else if (this.tabDoJogo.haMarcaNaPosicao (0,0) && this.tabDoJogo.getMarcaNaPosicao (0,0).equals (this.marca) &&
                     this.tabDoJogo.haMarcaNaPosicao (0,1) && this.tabDoJogo.getMarcaNaPosicao (0,1).equals (this.marca) &&
                    !this.tabDoJogo.haMarcaNaPosicao (0,2))
                this.tabDoJogo.setMarcaNaPosicao (this.marca, 0,2);
            // Tentando ganhar nas demais linhas, colunas e diagonais.
            else if (this.tenteJogarNaTrinca (this.marca, 2, 1,0, 1,1, 1,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 2,0, 2,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 0,0, 1,0, 2,0)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 0,1, 1,1, 2,1)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 0,2, 1,2, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 0,0, 1,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 2, 0,2, 1,1, 2,0)) return;
            // Tentando atrapalhar a vitória: linhas 0 a 2, colunas 0 a 2, diagonais.
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,0, 0,1, 0,2)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 1,0, 1,1, 1,2)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 2,0, 2,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,0, 1,0, 2,0)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,1, 1,1, 2,1)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,2, 1,2, 2,2)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,0, 1,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (adversaria, 2, 0,2, 1,1, 2,0)) return;
            // Preparando uma vitória: uma marca própria e duas posições vazias.
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,0, 0,1, 0,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 1,0, 1,1, 1,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 2,0, 2,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,0, 1,0, 2,0)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,1, 1,1, 2,1)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,2, 1,2, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,0, 1,1, 2,2)) return;
            else if (this.tenteJogarNaTrinca (this.marca, 1, 0,2, 1,1, 2,0)) return;
            else
            {
                // Sem combinação prioritária, ocupa a primeira posição livre.
                for (int lin=0; lin<3; lin++)
                    for (int col=0; col<3; col++)
                        if (!this.tabDoJogo.haMarcaNaPosicao (lin, col))
                        {
                            this.tabDoJogo.setMarcaNaPosicao (this.marca, lin, col);
                            return;
                        }

                throw new Exception ("Tabuleiro cheio");
            }
        }
        catch (Exception erro)
        {
            throw new IllegalStateException ("Não foi possível realizar a jogada automática", erro);
        }
    }

    @Override
    public String toString ()
    {
        return "Jogador automático\nMarca: "+this.marca+"\nTabuleiro:\n"+this.tabDoJogo;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (obj==null) return false;
        if (this.getClass()!=obj.getClass()) return false;

        JogadorAutomatico jogador = (JogadorAutomatico)obj;
        return this.marca.equals (jogador.marca) && this.tabDoJogo.equals (jogador.tabDoJogo);
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 2*ret + this.marca.hashCode ();
        ret = 2*ret + this.tabDoJogo.hashCode ();

        if (ret<0) ret = -ret;
        if (ret<0) ret = 0;
        return ret;
    }

    // Construtor de cópia.
    public JogadorAutomatico (JogadorAutomatico modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.marca = modelo.marca;
        this.tabDoJogo = new Tabuleiro (modelo.tabDoJogo);
    }

    @Override
    public Object clone ()
    {
        try
        {
            return new JogadorAutomatico (this);
        }
        catch (Exception erro)
        {
            throw new IllegalStateException ("Não foi possível copiar o jogador automático", erro);
        }
    }
}
