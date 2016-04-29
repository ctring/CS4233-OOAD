require './Player.rb'
  
def runGame(blue, red)
#  cmd = "java -classpath Hanto.jar:./players/#{blue.jarFile}:./players/#{red.jarFile} hanto.tournament.TournamentRunner #{blue.className} #{red.className}"
#  puts cmd
  puts `java -classpath Hanto.jar;./players/#{blue.jarFile};./players/#{red.jarFile} hanto.tournament.TournamentRunner #{blue.className} #{red.className}`
end

blue_name = "student" + ARGV[0]
red_name = blue_name
red_name = "student" + ARGV[1] if (ARGV.size == 2)
blue = Player.new(blue_name)
red = Player.new(red_name)
runGame(blue, red)