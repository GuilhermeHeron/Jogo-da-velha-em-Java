import jogo.*;
import jogador.*;

public class Velha
{
    public static void main (String args [])
    {
        Tabuleiro           tabDoJogo;

        Jogador             jogador  = null;
        JogadorHumano       jogadorH = null;
        JogadorAutomatico   jogadorA = null;

        AnalistaSituacional analista;

        boolean jogadorHumanoComeca = (int)(Math.random ()*2) == 0;

        do
        {
            try
            {
                tabDoJogo = new Tabuleiro ();

                if (jogadorHumanoComeca)
                {
                    jogadorH = new JogadorHumano (tabDoJogo);
                    jogadorA = new JogadorAutomatico
                              (tabDoJogo,
                               new Marca (jogadorH.getMarca ().getSimbolo ()=='X'?'O':'X'));
                }
                else
                {
                    jogadorA = new JogadorAutomatico (tabDoJogo);
                    jogadorH = new JogadorHumano
                              (tabDoJogo,
                               new Marca (jogadorA.getMarca ().getSimbolo ()=='X'?'O':'X'));
                }

                analista = new AnalistaSituacional (tabDoJogo);
                System.out.println ("Você joga com "+jogadorH.getMarca ()+".");
                System.out.println (jogadorHumanoComeca ? "Você começa." : "O computador começa.");
                System.out.println (tabDoJogo);

                if (jogadorHumanoComeca)
                    jogador = jogadorH;
                else
                    jogador = jogadorA;

                do
                {
                    jogador.facaSuaJogada ();
                    System.out.println (tabDoJogo);

                    if (jogador==jogadorH)
                        jogador = jogadorA;
                    else
                        jogador = jogadorH;
                }
                while (!analista.fimDeJogo ());

                Marca vencedor = analista.getMarcaDoVencedor ();
                if (vencedor==null)
                    jogadorH.deuVelha ();
                else if (vencedor.equals (jogadorH.getMarca ()))
                    jogadorH.vcGanhou ();
                else
                    jogadorH.vcPerdeu ();

                jogadorHumanoComeca = !jogadorHumanoComeca;
            }
            catch (Exception erro)
            {
                System.err.println (erro.getMessage ());
                return;
            }
        }
        while (jogadorH.querMaisUmaPartida ());
    }
}
