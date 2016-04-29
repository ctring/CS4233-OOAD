class Player
  attr_reader :name, :jarFile, :className, :playerNo
  attr_accessor :won, :lost, :draw, :error, :unknown, :score
  @@index = 1
  
  def initialize(name)
    @name = name
    @jarFile = "#{name}.jar"
    @className = "hanto.#{name}.tournament.HantoPlayer"
    @won = 0
    @lost = 0
    @draw = 0
    @unknown = 0
    @error = 0
    @playerNo = @@index
    @@index += 1
    @score = 0
  end
  
#  def initialize(name, jarfile, classname)
#    @name, @jarFile, @className = name, jarfile, classname
#    @won = 0
#    @lost = 0
#    @draw = 0
#    @unknown = 0
#    @playerNo = @@index
#    @@index += 1
#  end
  
  def to_s
    "#{@playerNo}\t#{@name}\t#{@won.to_s}\t#{@lost}\t#{@draw}\t#{@error}"
  end
end