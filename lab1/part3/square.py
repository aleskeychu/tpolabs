#!/usr/bin/env python3

class Crowd:

    def __init__(self):
        self.n_noises_made = 0

class Platform:

    def __init__(self):
        pass

class Speaker:

    def __init__(self):
        self.speeches_completed = 0


class Arthur:

    def __init__(self):
        self.state = "on the ground"


class Square:

    @property
    def crowd(self):
        return self._crowd

    @property
    def speaker(self):
        return self._speaker

    @property
    def platform(self):
        return self._platform

    @property
    def arthur(self):
        return self._arthur

    def __init__(self):
        self._crowd = None
        # Arthur is not there right now
        self._arthur = None
        # Platform is not there neither
        self._platform = None
        self._speaker = None

    def bring_crowd(self):
        ''' Adds the crowd to the Square

        Returns:
            Crowd on success, None otherwise
        '''

        if self._crowd is None:
            self._crowd = Crowd()
            self._arthur = Arthur()
            return self._crowd
        else:
            # one can not bring the second crowd to the square
            raise RuntimeError("You tried to create second crowd,"
                    "that's not possible.")

    def construct_platform(self):
        ''' Adds the platform to the Square

        Returns:
            Platform on success, None otherwise
        '''

        if self._platform is None:
            self._platform = Platform()
            return self._platform
        else:
            raise RuntimeError("Can not create the 2nd platform on one square")

    def invite_speaker(self):
        ''' Adds the speaker to the Square

        Returns:
            Speaker on success, None otherwise
        '''

        if self._speaker is None and self._platform is not None:
            self._speaker = Speaker()
            return self._speaker
        elif self._speaker is not None:
            raise ValueError("Speaker is already there")
        elif self._platform is None:
            raise ValueError("There is no platform to invite speaker to")
        else:
            # should i raise something there?
            return None

    def speaker_talk(self):
        # for this to work speaker should be created and "invited"
        if self._speaker is None:
            raise ValueError("Please, invite speaker first before his speech")

        self.speaker.speeches_completed += 1

    def make_noise(self):
        ''' Method for "activating" crowd and making Arthur fly.

        raises: ValueError -- if there is no crowd
        '''

        if self._crowd is None:
            raise ValueError("There is no crowd in the square")
        else:
            self.crowd.n_noises_made += 1

        # This check will make Arthur flying if and only if:
        # * orator is here and gave at least one speech
        # * crowd made noise at least once
        if self.crowd.n_noises_made > 1 \
                and self._platform is not None \
                and self._speaker is not None \
                and self._speaker.speeches_completed > 0:
            # make Arthur fly
            self.arthur.state = "flying"


def main():
    pass


if __name__ == '__main__':
    main()
