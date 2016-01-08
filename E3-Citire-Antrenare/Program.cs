using System;
using System.Windows.Forms;
using RNpredictivClass;

namespace RNpredictiv
{
    class Program
    {
        [STAThread]
        private static void Main()
        {
            Console.BufferHeight = Int16.MaxValue - 1;
            OpenFileDialog ofd = new OpenFileDialog();
            ConsoleKeyInfo option;
            do
            {
                Console.WriteLine("Apasa 1 pentru antrenare RN");
                Console.WriteLine("Apasa 2 pentru a citi .wav");
                Console.WriteLine("Apasa Esc pentru a iesi din aplicatie");
                option = Console.ReadKey();
                switch (option.KeyChar)
                {
                    case '1':
                        ofd.Filter = "(*.txt| *.txt";
                        if (ofd.ShowDialog() == DialogResult.OK)
                        {
                            var filename = ofd.FileName;
                            Console.WriteLine(filename);
                            Neuron n = new Neuron();
                            try
                            {
                                n.SetValues(@filename);
                                n.Antrenare();
                            }
                            catch (Exception e)
                            {
                                Console.WriteLine("Not valid values of double!\n{0}",e.Message);
                            }                         
                        }
                        break;
                    case '2':
                        ofd.Filter = "(*.wav| *.wav";
                        if (ofd.ShowDialog() == DialogResult.OK)
                        {
                            var filename = ofd.FileName;
                            Console.WriteLine(filename);
                            Neuron n = new Neuron();
                            n.ParseWav(@filename);
                        }
                        break;
                    default:
                        Console.WriteLine();
                        break;
                }                 
            } while (option.Key != ConsoleKey.Escape);
        }
    }
}
