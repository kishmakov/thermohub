# Compiler Settings
###################

# dirs
SRC_DIR = src
OBJ_DIR = obj

# file sets
CPP_FILES = $(wildcard $(SRC_DIR)/*.cpp) $(wildcard $(SRC_DIR)/**/*.cpp)
OBJ_FILES = $(strip $(patsubst %.cpp, %.o, $(notdir $(CPP_FILES))))
FOBJ_FILES = $(addprefix $(OBJ_DIR)/, $(OBJ_FILES))

#options
CC = g++-4.6
WARNING_FLAGS = -Wall -Wextra
CFLAGS = -O2 -g -pthread -fPIC -std=c++0x $(WARNING_FLAGS) $(addprefix -I, $(SRC_DIR))
LFLAGS = -pthread

# Main Targets
##############

all: verbose-link

# Compilation Tasks
###################

announce-compile:
	@echo "*** compile ***"

create-obj-dir:
	@mkdir -p $(OBJ_DIR)

define rul_gen
$(patsubst %.cpp, %.o, $(notdir $(1))): $(1)
	$(CC) $(CFLAGS) -c $$< -o $(OBJ_DIR)/$$@
endef

$(foreach file, $(CPP_FILES), $(eval $(call rul_gen, $(file))))

verbose-compile: announce-compile create-obj-dir $(OBJ_FILES)

# Linking Tasks
###############

# tester
verbose-link: verbose-compile announce-link link

announce-link:
	@echo "*** link ***"

link:
	$(CC) -o run $(FOBJ_FILES) $(LFLAGS)

# Cleaning
##########

clean:
	@rm run
	@rm -rf $(OBJ_DIR)

